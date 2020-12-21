package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardDto;
import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.ReviewBoardCommentRepository;
import com.springboot.intelllij.repository.ReviewBoardPreviewRepository;
import com.springboot.intelllij.repository.ReviewBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.*;

@Service
public class ReviewBoardService {

    @Autowired
    private ReviewBoardRepository reviewBoardRepo;
    @Autowired
    private ReviewBoardPreviewRepository reviewBoardPreviewRepository;
    @Autowired
    private ReviewBoardCommentRepository reviewBoardCommentRepository;

    public List<ReviewBoardEntity> getAllPosts() { return reviewBoardRepo.findAll(); }

    public ReviewBoardEntity postLiked(Integer id) {
        ReviewBoardEntity reviewBoardEntity = reviewBoardRepo.findById(id).
                orElseThrow(() -> new NotFoundException(BOARD_NOT_FOUND));
        reviewBoardEntity.setLikeCnt(reviewBoardEntity.getLikeCnt() + 1);
        reviewBoardEntity = reviewBoardRepo.save(reviewBoardEntity);
        return reviewBoardEntity;
    }

    public ReviewBoardViewEntity getReviewBoardById(Integer id) {
        return reviewBoardPreviewRepository.findById(id).orElseThrow(()-> new NotFoundException(BOARD_NOT_FOUND));
    }

    public ResponseEntity addPostToReviewBoard(ReviewBoardDto reviewBoardDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReviewBoardEntity reviewBoard = new ReviewBoardEntity(reviewBoardDto,principal.toString());
        reviewBoardRepo.save(reviewBoard);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity deletePostAndComments(Integer id) {
        reviewBoardCommentRepository.deleteByPostId(id);
        ReviewBoardEntity reviewBoardEntity = reviewBoardRepo.findById(id).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
        reviewBoardRepo.delete(reviewBoardEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updatePost(Integer id, String title, String content) {
        reviewBoardRepo.updateReviewBoardEntity(id, title, content);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
