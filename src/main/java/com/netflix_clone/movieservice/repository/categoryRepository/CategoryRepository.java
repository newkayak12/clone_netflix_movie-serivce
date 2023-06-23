package com.netflix_clone.movieservice.repository.categoryRepository;

import com.netflix_clone.movieservice.repository.domain.Category;
import com.netflix_clone.movieservice.repository.dto.reference.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    @Query(nativeQuery = true, value = "SELECT :msg FROM DUAL;")
    String wakeUpMsg(@Param(value = "msg") String Msg);

    Category findCategoryByCategoryNo(Long parentNo);
}
