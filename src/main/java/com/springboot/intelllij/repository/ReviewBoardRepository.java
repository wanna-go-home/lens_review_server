package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoardEntity, Integer> {
    List<ReviewBoardEntity> findByAccountId(Integer accountId);

    @Modifying
    @Query("update ReviewBoardEntity e set e.title = :title, e.content = :content where e.id = :id")
    void updateReviewBoardEntity(
            @Param(value = "id") Integer id, @Param(value = "title") String title, @Param(value = "content") String content
    );

    @Modifying
    @Query("UPDATE ReviewBoardEntity e SET e.likeCnt = e.likeCnt + 1 WHERE e.id = :id")
    void increaseLikeCnt(@Param(value = "id") Integer id);

    @Modifying
    @Query("UPDATE ReviewBoardEntity e SET e.likeCnt = e.likeCnt - 1 WHERE e.id = :id")
    void decreaseLikeCnt(@Param(value = "id") Integer id);
}
