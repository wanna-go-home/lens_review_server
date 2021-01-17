package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.LikedHistoryEntity;
import com.springboot.intelllij.constant.LikeableTables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeHistoryRepository extends JpaRepository<LikedHistoryEntity, Integer> {
    Optional<LikedHistoryEntity> findByAccountIdAndLikeableTableAndTableContentId(
            Integer accountId, LikeableTables likeableTable, Integer tableContentId);
}
