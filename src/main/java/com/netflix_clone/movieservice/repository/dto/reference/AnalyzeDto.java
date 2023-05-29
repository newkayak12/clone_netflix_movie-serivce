package com.netflix_clone.movieservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

/**
 * A DTO for the {@link com.netflix_clone.movieservice.repository.domain.Analyze} entity
 */
@Data
public class AnalyzeDto implements Serializable {
    private Long analyzeNo;
    private ContentsInfoDto contentsInfo;
    private CategoryDto category;
    private Double watchScore;
    private Long favoriteScore;

    @QueryProjection

    public AnalyzeDto(Long analyzeNo, ContentsInfoDto contentsInfo, CategoryDto category, Double watchScore, Long favoriteScore) {
        this.analyzeNo = analyzeNo;
        this.contentsInfo = contentsInfo;
        this.category = category;
        this.watchScore = watchScore;
        this.favoriteScore = favoriteScore;
    }
}