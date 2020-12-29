package com.springboot.intelllij.utils;

import com.springboot.intelllij.domain.CommentBaseEntity;

import java.util.Comparator;

public class CommentComparator implements Comparator<CommentBaseEntity> {

    @Override
    public int compare(CommentBaseEntity o1, CommentBaseEntity o2) {
        if(o1.getCreatedAt().isBefore(o2.getCreatedAt())) return 1;
        else if(o2.getCreatedAt().isBefore(o1.getCreatedAt())) return -1;
        else return 0;
    }
}
