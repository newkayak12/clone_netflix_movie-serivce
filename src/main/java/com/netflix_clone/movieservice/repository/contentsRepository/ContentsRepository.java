package com.netflix_clone.movieservice.repository.contentsRepository;

import com.netflix_clone.movieservice.repository.domain.ContentsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends JpaRepository<ContentsInfo, Long>, ContentsRepositoryCustom {
}
