package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.*;

@Service
public class LikePostCommentService {

    @Autowired
    LikeHistoryRepository likeHistoryRepository;

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @Autowired
    ReviewBoardRepository reviewBoardRepository;

    @Autowired
    ReviewBoardPreviewRepository reviewBoardPreviewRepository;

    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepository;

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepository;

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public FreeBoardEntity likeFreeboardPost(LikeableTables likeableTable, Integer id) {
        AccountEntity user= UserUtils.getUserEntity();
        Integer accountId = user.getId();
        Optional<LikedHistoryEntity> likedHistoryEntity =
                likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(accountId, likeableTable, id);
        FreeBoardEntity freeBoardEntity = freeBoardRepository.findById(id).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, id);
            likeHistoryRepository.save(newLikedHistoryEntity);
            freeBoardEntity.setLikeCnt(freeBoardEntity.getLikeCnt() + 1);
            freeBoardEntity = freeBoardRepository.save(freeBoardEntity);
        }
        freeBoardEntity = EntityUtils.setIsAuthor(freeBoardEntity, accountId);
        freeBoardEntity = EntityUtils.setIsLiked(freeBoardEntity, accountId, LikeableTables.FREE_BOARD, id);
        return freeBoardEntity;
    }

    @Transactional
    public FreeBoardEntity unlikeFreeBoardPost(LikeableTables likeableTable, Integer id) {
        AccountEntity user = UserUtils.getUserEntity();
        Integer accountId = user.getId();
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
        return freeBoardEntity;
    }

    @Transactional
    public ReviewBoardViewEntity likeReviewBoardPost(LikeableTables likeableTable, Integer id) {
        AccountEntity user = UserUtils.getUserEntity();
        Integer accountId = user.getId();
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
        ReviewBoardViewEntity reviewBoardViewEntity = reviewBoardPreviewRepository.findById(id).orElseThrow(
                () -> new NotFoundException(POST_NOT_FOUND)
        );
        reviewBoardViewEntity.setLikeCnt(reviewBoardEntity.getLikeCnt());
        reviewBoardViewEntity = EntityUtils.setIsAuthor(reviewBoardViewEntity, accountId);
        reviewBoardViewEntity = EntityUtils.setIsLiked(reviewBoardViewEntity, accountId, LikeableTables.REVIEW_BOARD, id);
        return reviewBoardViewEntity;
    }

    @Transactional
    public ReviewBoardViewEntity unlikeReviewBoardPost(LikeableTables likeableTable, Integer id) {
        AccountEntity user = UserUtils.getUserEntity();
        Integer accountId = user.getId();
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
        ReviewBoardViewEntity reviewBoardViewEntity = reviewBoardPreviewRepository.findById(id).orElseThrow(
                () -> new NotFoundException(POST_NOT_FOUND)
        );
        reviewBoardViewEntity.setLikeCnt(reviewBoardEntity.getLikeCnt());
        reviewBoardViewEntity = EntityUtils.setIsAuthor(reviewBoardViewEntity, accountId);
        reviewBoardViewEntity = EntityUtils.setIsLiked(reviewBoardViewEntity, accountId, LikeableTables.REVIEW_BOARD, id);
        return reviewBoardViewEntity;
    }

    @Transactional
    public CommentOutputDTO likeFreeBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();

        Optional<LikedHistoryEntity> likedHistoryEntity =
                likeHistoryRepository.findByAccountIdAndLikeableTableAndTableContentId(accountId, likeableTable, commentId);

        FreeBoardCommentEntity freeBoardCommentEntity = freeBoardCommentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(COMMENT_NOT_FOUND)
        );
        if(!likedHistoryEntity.isPresent()) {
            LikedHistoryEntity newLikedHistoryEntity = new LikedHistoryEntity(accountId, likeableTable, commentId);
            likeHistoryRepository.save(newLikedHistoryEntity);
            freeBoardCommentEntity.setLikeCnt(freeBoardCommentEntity.getLikeCnt() + 1);
            freeBoardCommentEntity = freeBoardCommentRepository.save(freeBoardCommentEntity);
        }
        AccountEntity author = accountRepository.findById(freeBoardCommentEntity.getAccountId()).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND)
        );
        CommentOutputDTO comment = new CommentOutputDTO(freeBoardCommentEntity, author.getNickname());
        comment = EntityUtils.setIsAuthor(comment, accountId);
        comment = EntityUtils.setIsLiked(comment, accountId, LikeableTables.FREE_BOARD_COMMENT, commentId);
        return comment;
    }

    @Transactional
    public CommentOutputDTO unlikeFreeBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
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
        AccountEntity author = accountRepository.findById(freeBoardCommentEntity.getAccountId()).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND)
        );
        CommentOutputDTO comment = new CommentOutputDTO(freeBoardCommentEntity, author.getNickname());
        comment = EntityUtils.setIsAuthor(comment, accountId);
        comment = EntityUtils.setIsLiked(comment, accountId, LikeableTables.FREE_BOARD_COMMENT, commentId);
        return comment;
    }

    @Transactional
    public CommentOutputDTO likeReviewBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
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
        AccountEntity author = accountRepository.findById(reviewBoardCommentEntity.getAccountId()).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND)
        );
        CommentOutputDTO comment = new CommentOutputDTO(reviewBoardCommentEntity, author.getNickname());
        comment = EntityUtils.setIsAuthor(comment, accountId);
        comment = EntityUtils.setIsLiked(comment, accountId, LikeableTables.REVIEW_BOARD_COMMENT, commentId);
        return comment;
    }

    @Transactional
    public CommentOutputDTO unlikeReviewBoardComment(LikeableTables likeableTable, Integer postId, Integer commentId) {
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
        AccountEntity author = accountRepository.findById(reviewBoardCommentEntity.getAccountId()).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND)
        );
        CommentOutputDTO comment = new CommentOutputDTO(reviewBoardCommentEntity, author.getNickname());
        comment = EntityUtils.setIsAuthor(comment, accountId);
        comment = EntityUtils.setIsLiked(comment, accountId, LikeableTables.REVIEW_BOARD_COMMENT, commentId);
        return comment;
    }
}
