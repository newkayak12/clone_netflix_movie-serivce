package com.netflix_clone.movieservice.repository.contentPersonRepository;

import com.netflix_clone.movieservice.repository.domain.ContentPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentPersonRepository extends JpaRepository<ContentPerson, Long> {
}
