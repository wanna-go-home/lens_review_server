package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.COMMENT_NOT_FOUND;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.POST_NOT_FOUND;

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

        FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(id).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, id);
            likeHistoryRepository.save(newLikedHistoryEntity);
            freeBoardEntity.setLikeCnt(freeBoardEntity.getLikeCnt() + 1);
            freeBoardEntity = freeBoardRepository.save(freeBoardEntity);
        }
        freeBoardEntity = EntityUtils.setIsAuthor(freeBoardEntity, accountId);
        freeBoardEntity = EntityUtils.setIsLiked(freeBoardEntity, accountId, LikeableTables.FREE_BOARD, id);
        return new ResponseEntity<>(freeBoardEntity, HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity unlikeFreeBoardPost(LikeableTables likeableTable, Integer id) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, id
        );

        FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(id).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            freeBoardEntity.setLikeCnt(freeBoardEntity.getLikeCnt() - 1);
            freeBoardEntity = freeBoardRepository.save(freeBoardEntity);
        }
        freeBoardEntity = EntityUtils.setIsAuthor(freeBoardEntity, accountId);
        freeBoardEntity = EntityUtils.setIsLiked(freeBoardEntity, accountId, LikeableTables.FREE_BOARD, id);
        return new ResponseEntity<>(freeBoardEntity, HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity likeReviewBoardPost(LikeableTables likeableTable, Integer id) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, id
        );

        ReviewBoardEntity reviewBoardEntity = reviewBoardRepository.findById(id).orElseThrow(
                () -> new NotFoundException(POST_NOT_FOUND)
        );
        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, id);
            likeHistoryRepository.save(newLikedHistoryEntity);
            reviewBoardEntity.setLikeCnt(reviewBoardEntity.getLikeCnt() + 1);
            reviewBoardEntity = reviewBoardRepository.save(reviewBoardEntity);
        }
        reviewBoardEntity = EntityUtils.setIsAuthor(reviewBoardEntity, accountId);
        reviewBoardEntity = EntityUtils.setIsLiked(reviewBoardEntity, accountId, LikeableTables.REVIEW_BOARD, id);
        return new ResponseEntity<>(reviewBoardEntity, HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity unlikeReviewBoardPost(LikeableTables likeableTable, Integer id) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, id
        );

        ReviewBoardEntity reviewBoardEntity = reviewBoardRepository.findById(id).orElseThrow(
                () -> new NotFoundException(POST_NOT_FOUND)
        );
        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            reviewBoardEntity.setLikeCnt(reviewBoardEntity.getLikeCnt() - 1);
            reviewBoardEntity = reviewBoardRepository.save(reviewBoardEntity);
        }
        reviewBoardEntity = EntityUtils.setIsAuthor(reviewBoardEntity, accountId);
        reviewBoardEntity = EntityUtils.setIsLiked(reviewBoardEntity, accountId, LikeableTables.REVIEW_BOARD, id);
        return new ResponseEntity<>(reviewBoardEntity, HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity likeFreeBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();

        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        FreeBoardCommentEntity freeBoardCommentEntity = freeBoardCommentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(COMMENT_NOT_FOUND)
        );
        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, commentId);
            likeHistoryRepository.save(newLikedHistoryEntity);
            freeBoardCommentEntity.setLikeCnt(freeBoardCommentEntity.getLikeCnt() + 1);
            freeBoardCommentEntity = freeBoardCommentRepository.save(freeBoardCommentEntity);
        }
        freeBoardCommentEntity = EntityUtils.setIsAuthor(freeBoardCommentEntity, accountId);
        freeBoardCommentEntity = EntityUtils.setIsLiked(freeBoardCommentEntity, accountId, LikeableTables.FREE_BOARD_COMMENT, commentId);
        return new ResponseEntity<>(freeBoardCommentEntity, HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity unlikeFreeBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        FreeBoardCommentEntity freeBoardCommentEntity = freeBoardCommentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(COMMENT_NOT_FOUND)
        );
        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            freeBoardCommentEntity.setLikeCnt(freeBoardCommentEntity.getLikeCnt() - 1);
            freeBoardCommentEntity = freeBoardCommentRepository.save(freeBoardCommentEntity);
        }
        freeBoardCommentEntity = EntityUtils.setIsAuthor(freeBoardCommentEntity, accountId);
        freeBoardCommentEntity = EntityUtils.setIsLiked(freeBoardCommentEntity, accountId, LikeableTables.FREE_BOARD_COMMENT, commentId);
        return new ResponseEntity<>(freeBoardCommentEntity, HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity likeReviewBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        ReviewBoardCommentEntity reviewBoardCommentEntity = reviewBoardCommentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(COMMENT_NOT_FOUND)
        );
        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, commentId);
            likeHistoryRepository.save(newLikedHistoryEntity);
            reviewBoardCommentEntity.setLikeCnt(reviewBoardCommentEntity.getLikeCnt() + 1);
            reviewBoardCommentEntity = reviewBoardCommentRepository.save(reviewBoardCommentEntity);
        }
        reviewBoardCommentEntity = EntityUtils.setIsAuthor(reviewBoardCommentEntity, accountId);
        reviewBoardCommentEntity = EntityUtils.setIsLiked(reviewBoardCommentEntity, accountId, LikeableTables.REVIEW_BOARD_COMMENT, commentId);
        return new ResponseEntity<>(reviewBoardCommentEntity, HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity unlikeReviewBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        Optional<LikedHistoryEntity> likedHistoryEntity = likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(
                accountId, likeableTable, commentId
        );

        ReviewBoardCommentEntity reviewBoardCommentEntity = reviewBoardCommentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(COMMENT_NOT_FOUND)
        );
        if(likedHistoryEntity.isPresent()) {
            likeHistoryRepository.delete(likedHistoryEntity.get());
            reviewBoardCommentEntity.setLikeCnt(reviewBoardCommentEntity.getLikeCnt() - 1);
            reviewBoardCommentEntity = reviewBoardCommentRepository.save(reviewBoardCommentEntity);
        }
        reviewBoardCommentEntity = EntityUtils.setIsAuthor(reviewBoardCommentEntity, accountId);
        reviewBoardCommentEntity = EntityUtils.setIsLiked(reviewBoardCommentEntity, accountId, LikeableTables.REVIEW_BOARD_COMMENT, commentId);
        return new ResponseEntity<>(reviewBoardCommentEntity, HttpStatus.ACCEPTED);
    }
}
