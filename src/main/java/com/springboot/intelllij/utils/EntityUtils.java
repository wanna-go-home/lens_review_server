package com.springboot.intelllij.utils;

import com.springboot.intelllij.domain.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class EntityUtils {

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
}
