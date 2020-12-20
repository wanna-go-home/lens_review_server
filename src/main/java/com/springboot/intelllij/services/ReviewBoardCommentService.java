package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.ReviewBoardCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.COMMENT_NOT_FOUND;

@Service
public class ReviewBoardCommentService {

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepo;

    public ResponseEntity post(ReviewBoardCommentEntity comment) {
        reviewBoardCommentRepo.save(comment);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public List<ReviewBoardCommentEntity> getCommentByPostId(Integer postId) {
        List<ReviewBoardCommentEntity> comments = reviewBoardCommentRepo.findByPostId(postId);

        Comparator<ReviewBoardCommentEntity> comparator = Comparator.comparing(ReviewBoardCommentEntity::getCreatedAt);
        comparator = comparator.thenComparingInt(ReviewBoardCommentEntity::getBundleId);
        comments.sort(comparator);

        return comments;
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
