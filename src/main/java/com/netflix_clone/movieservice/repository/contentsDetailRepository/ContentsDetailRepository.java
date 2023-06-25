package com.netflix_clone.movieservice.repository.contentsDetailRepository;

import com.netflix_clone.movieservice.repository.domain.ContentsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentsDetailRepository extends JpaRepository<ContentsDetail, Long> {

    ContentsDetail findContentsDetailByDetailNo(Long detailNo);
    List<ContentsDetail> findContentsDetailByContentsInfo_ContentsNo(Long contentsNo);
}
