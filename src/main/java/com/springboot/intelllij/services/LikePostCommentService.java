package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.LikedHistoryEntity;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikePostCommentService {

    @Autowired
    LikeHistoryRepository likeHistoryRepository;

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @Autowired
    ReviewBoardRepository reviewBoardRepository;

    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepository;

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepository;

    @Transactional
    public ResponseEntity likeFreeboardPost(LikeableTables likeableTable, Integer id) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, id
        );

        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, id);
            likeHistoryRepository.save(newLikedHistoryEntity);
            freeBoardRepository.increaseLikeCnt(id);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity unlikeFreeBoardPost(LikeableTables likeableTable, Integer id) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, id
        );

        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            freeBoardRepository.decreaseLikeCnt(id);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity likeReviewBoardPost(LikeableTables likeableTable, Integer id) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, id
        );

        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, id);
            likeHistoryRepository.save(newLikedHistoryEntity);
            reviewBoardRepository.increaseLikeCnt(id);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity unlikeReviewBoardPost(LikeableTables likeableTable, Integer id) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, id
        );

        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            reviewBoardRepository.decreaseLikeCnt(id);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity likeFreeBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();

        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, commentId);
            likeHistoryRepository.save(newLikedHistoryEntity);
            freeBoardCommentRepository.increaseLikeCnt(commentId);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity unlikeFreeBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            freeBoardCommentRepository.decreaseLikeCnt(commentId);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity likeReviewBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, commentId);
            likeHistoryRepository.save(newLikedHistoryEntity);
            reviewBoardCommentRepository.increaseLikeCnt(commentId);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity unlikeReviewBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            reviewBoardCommentRepository.decreaseLikeCnt(commentId);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
