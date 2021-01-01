package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Integer> {
    List<FreeBoardEntity> findByAccountId(Integer accountId);

    @Modifying
    @Query("update FreeBoardEntity e set e.title = :title, e.content = :content where e.id = :id")
    void updateFreeBoardEntity(
        @Param(value = "id") Integer id, @Param(value = "title") String title, @Param(value = "content") String content
    );

}
