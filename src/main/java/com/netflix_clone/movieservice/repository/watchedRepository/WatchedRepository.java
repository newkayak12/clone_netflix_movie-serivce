package com.netflix_clone.movieservice.repository.watchedRepository;

import com.netflix_clone.movieservice.repository.domain.Watched;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedRepository extends JpaRepository<Watched, Long>, WatchedRepositoryCustom {
}
