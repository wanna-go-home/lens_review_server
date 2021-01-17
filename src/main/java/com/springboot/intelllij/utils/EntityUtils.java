package com.springboot.intelllij.utils;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.BaseEntity;
import com.springboot.intelllij.domain.LikedHistoryEntity;
import com.springboot.intelllij.repository.LikeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EntityUtils {

    @Autowired
    LikeHistoryRepository likeHistoryRepository;

    public static List<? extends BaseEntity> setIsAuthor(List<? extends BaseEntity> entityList, int accountId) {
        return entityList.stream().map(entity -> {
            entity.setIsAuthor(entity.getAccountId() == accountId);
            return entity;
        }).collect(Collectors.toList());
    }

    public static <T extends BaseEntity> T setIsAuthor(T entity, int accountId) {
        entity.setIsAuthor(entity.getAccountId() == accountId);
        return entity;
    }

    // TODO : Insert this method into service after genericize services
    public <R extends BaseEntity> List<R> setIsLiked(List<R> entityList, int accountId, LikeableTables likeableTables) {
        List<LikedHistoryEntity> likedHistoryList = likeHistoryRepository.findByAccountIdAndLikeableTable(accountId, likeableTables);
        Set<Integer> likedSet = likedHistoryList.stream().map(LikedHistoryEntity::getTableContentId).collect(Collectors.toSet());

        entityList = entityList.stream().map(baseEntity -> {
            Boolean isLiked = likedSet.contains(baseEntity.getId());
            baseEntity.setIsLiked(isLiked);
            return baseEntity;
        }).collect(Collectors.toList());
        return entityList;
    }

    public <R extends BaseEntity> R setIsLiked(R entity, int accountId, LikeableTables likeableTables, int tableContentId) {
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTables, tableContentId
        );
        entity.setIsLiked(likedHistoryEntity.isPresent());
        return entity;
    }
}
