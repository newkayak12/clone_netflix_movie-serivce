package com.netflix_clone.movieservice.repository.personRepository;

import com.netflix_clone.movieservice.repository.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, PersonRepositoryCustom {
        public Person findPersonByPersonNo(Long personNo);

}
