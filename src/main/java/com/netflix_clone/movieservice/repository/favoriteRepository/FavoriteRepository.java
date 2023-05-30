package com.netflix_clone.movieservice.repository.favoriteRepository;

import com.netflix_clone.movieservice.repository.domain.Favorite;
import com.netflix_clone.movieservice.repository.dto.reference.FavoriteWatchedDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteRepositoryCustom {

}
