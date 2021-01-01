package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.CommentDTO;
import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.ReviewBoardCommentRepository;
import com.springboot.intelllij.repository.ReviewBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.COMMENT_NOT_FOUND;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.POST_NOT_FOUND;

@Service
public class ReviewBoardCommentService {

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepo;

    @Autowired
    ReviewBoardRepository reviewBoardRepo;

    private final int COMMENT_MAX = 3;
    private final int COMMENT_DEPTH = 0;
    private final int CHILD_COMMENT_DEPTH = 1;

    public ResponseEntity post(Integer postId, CommentDTO comment) {
        ReviewBoardCommentEntity commentEntity = new ReviewBoardCommentEntity();
        ReviewBoardEntity review = reviewBoardRepo.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        Integer accountId = (Integer)SecurityContextHolder.getContext().getAuthentication().getDetails();
        commentEntity.setContent(comment.getContent());
        commentEntity.setPostId(postId);
        commentEntity.setCreatedAt(ZonedDateTime.now());
        commentEntity.setAccountId(accountId);

        if(comment.getBundleId() != null) {
            commentEntity.setBundleId(comment.getBundleId());
            commentEntity.setDepth(CHILD_COMMENT_DEPTH);
            reviewBoardCommentRepo.save(commentEntity);

            ReviewBoardCommentEntity parentEntity = reviewBoardCommentRepo.findById(comment.getBundleId())
                    .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
            parentEntity.increaseBundleSize();
            reviewBoardCommentRepo.save(parentEntity);
        } else {
            ReviewBoardCommentEntity savedEntity = reviewBoardCommentRepo.save(commentEntity);
            savedEntity.setBundleId(savedEntity.getId());
            reviewBoardCommentRepo.save(savedEntity);
        }

        review.increaseReplyCnt();
        reviewBoardRepo.save(review);
        
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public List<ReviewBoardCommentEntity> getCommentByPostId(Integer postId) {
        List<ReviewBoardCommentEntity> comments = reviewBoardCommentRepo.findByPostIdAndDepth(postId,COMMENT_DEPTH);
        List<ReviewBoardCommentEntity> resultCommentList = new ArrayList<>();

        Comparator<ReviewBoardCommentEntity> comparator = Comparator.comparing(ReviewBoardCommentEntity::getCreatedAt);
        comparator = comparator.thenComparingInt(ReviewBoardCommentEntity::getBundleId);
        comments.sort(comparator);

        for(ReviewBoardCommentEntity commentEntity : comments) {
            List<ReviewBoardCommentEntity> commentsOfComment = reviewBoardCommentRepo.findByBundleIdAndDepth(commentEntity.getId(),CHILD_COMMENT_DEPTH);
            resultCommentList.add(commentEntity);

            if(commentsOfComment.isEmpty()) continue;

            for(int i = 0; i < commentsOfComment.size(); i++) {
                if(i >= COMMENT_MAX) break;
                resultCommentList.add(commentsOfComment.get(i));
            }
        }

        return resultCommentList;
    }

    public List<ReviewBoardCommentEntity> getAllCommentByPostId(Integer postId, Integer commentId) {
        ReviewBoardCommentEntity originalComment = reviewBoardCommentRepo.findByPostIdAndDepth(postId,COMMENT_DEPTH)
                .stream().filter(reviewBoardCommentEntity -> reviewBoardCommentEntity.getId().equals(commentId)).findFirst()
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
        List<ReviewBoardCommentEntity> commentsOfComment = reviewBoardCommentRepo.findByBundleIdAndDepth(originalComment.getId(),CHILD_COMMENT_DEPTH);
        List<ReviewBoardCommentEntity> resultCommentList = new ArrayList<>();

        resultCommentList.add(originalComment);

        commentsOfComment.forEach(bundle -> {
            resultCommentList.add(bundle);
        });

        return resultCommentList;
    }

    public ReviewBoardCommentEntity updateComment(Integer postId, Integer commentId, String content) {
        ReviewBoardCommentEntity originalComment = reviewBoardCommentRepo.findById(commentId).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));

        originalComment.setContent(content);

        return reviewBoardCommentRepo.save(originalComment);
    }

    public ResponseEntity deleteComment(Integer postId, Integer commentId) {
        reviewBoardCommentRepo.deleteById(commentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
