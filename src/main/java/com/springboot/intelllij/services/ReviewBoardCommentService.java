package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import com.springboot.intelllij.repository.ReviewBoardCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;

@Service
public class ReviewBoardCommentService {

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepo;

    public ResponseEntity post(@RequestBody ReviewBoardCommentEntity comment) {
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
}
