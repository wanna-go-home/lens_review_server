package com.springboot.intelllij.utils;

import com.springboot.intelllij.domain.BoardBaseEntity;

import java.util.Comparator;

public class BoardComparator implements Comparator<BoardBaseEntity> {

    @Override
    public int compare(BoardBaseEntity o1, BoardBaseEntity o2) {
        if(o1.getCreatedAt().isBefore(o2.getCreatedAt())) return 1;
        else if (o2.getCreatedAt().isBefore(o1.getCreatedAt())) return -1;
        else return 0;
    }
}
