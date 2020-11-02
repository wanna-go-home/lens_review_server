package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import com.springboot.intelllij.repository.ReviewBoardCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewBoardCommentService {

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepo;

    public ResponseEntity post(ReviewBoardCommentEntity comment) {
        reviewBoardCommentRepo.save(comment);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
