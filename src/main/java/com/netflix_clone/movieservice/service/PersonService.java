package com.netflix_clone.movieservice.service;

import com.netflix_clone.movieservice.component.configure.feign.ImageFeign;
import com.netflix_clone.movieservice.component.enums.FileType;
import com.netflix_clone.movieservice.component.enums.Role;
import com.netflix_clone.movieservice.component.exceptions.BecauseOf;
import com.netflix_clone.movieservice.component.exceptions.CommonException;
import com.netflix_clone.movieservice.repository.domain.Person;
import com.netflix_clone.movieservice.repository.dto.reference.FileDto;
import com.netflix_clone.movieservice.repository.dto.reference.FileRequest;
import com.netflix_clone.movieservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.movieservice.repository.dto.reference.PersonDto;
import com.netflix_clone.movieservice.repository.dto.request.PersonRequest;
import com.netflix_clone.movieservice.repository.personRepository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.tomcat.jni.File;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final ModelMapper mapper;
    private final ImageFeign imageFeign;

    public Boolean save(PersonRequest request) {
        Person person = repository.save(mapper.map(request, Person.class));

        if(Objects.nonNull(request.getPersonNo()) && Objects.isNull(request.getFile())){
            FileType type = request.getRole().equals(Role.DIRECTOR) ? FileType.DIRECTOR : FileType.ACTOR;
            imageFeign.remove(request.getPersonNo(), type);
        }

        if(Objects.nonNull(request.getRawFile())){
            FileType type = request.getRole().equals(Role.DIRECTOR) ? FileType.DIRECTOR : FileType.ACTOR;
            FileRequest fileRequest = new FileRequest();
            fileRequest.setRawFile(request.getRawFile());
            fileRequest.setTableNo(person.getPersonNo());
            fileRequest.setFileType(type.name());

            imageFeign.save(fileRequest);
        }

        return true;
    }

    public Boolean remove(Long personNo) {
        Person person = repository.findPersonByPersonNo(personNo);
        FileType type = person.getRole().equals(Role.DIRECTOR) ? FileType.DIRECTOR : FileType.ACTOR;
        imageFeign.remove(personNo, type);
        repository.delete(person);
        return true;
    }

    @Transactional(readOnly = true)
    public PageImpl<PersonDto> people(PageableRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        return (PageImpl<PersonDto>) repository.people(pageable, request)
                                                .map( person -> {
                                                    FileType type = person.getRole().equals(Role.DIRECTOR) ? FileType.DIRECTOR : FileType.ACTOR;

                                                    FileDto file = Optional.ofNullable(imageFeign.file(person.getPersonNo(), type).getBody())
                                                                           .orElseGet(FileDto::new);

                                                    person.setFile(file);
                                                    return person;
                                                });
    }

    @Transactional(readOnly = true)
    public PersonDto person(Long personNo) throws CommonException {
        return repository.person(personNo)
                .map( person -> {
                    FileType type = person.getRole().equals(Role.DIRECTOR) ? FileType.DIRECTOR : FileType.ACTOR;
                    FileDto file = Optional.ofNullable(imageFeign.file(person.getPersonNo(), type).getBody())
                            .orElseGet(FileDto::new);
                    person.setFile(file);

                    if( Objects.isNull(person.getContentsInfoList()) ) person.setContentsInfoList(new ArrayList<>());

                    person.getContentsInfoList().forEach(content ->
                            content.setImages(
                                    Optional.ofNullable(
                                            imageFeign.files(content.getContentsNo(), FileType.THUMBNAIL).getBody()
                                    ).orElseGet(LinkedList::new)
                            )
                    );

                    return person;
                })
                .orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
    }
}
