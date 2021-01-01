package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.LensRepository;
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
    private LensRepository lensRepository;
    @Autowired
    private ReviewBoardPreviewRepository reviewBoardPreviewRepository;
    @Autowired
    private ReviewBoardCommentRepository reviewBoardCommentRepository;

    public List<ReviewBoardEntity> getAllPosts() { return reviewBoardRepo.findAll(); }

    @Transactional
    public ReviewBoardViewWithLensInfoEntity getReviewBoardById(Integer id) {
        ReviewBoardViewEntity reviewBoardViewEntity = reviewBoardPreviewRepository.findById(id).
                orElseThrow(()-> new NotFoundException(BOARD_NOT_FOUND));
        reviewBoardViewEntity.increaseReplyCnt();
        reviewBoardViewEntity = reviewBoardPreviewRepository.save(reviewBoardViewEntity);
        LensEntity lensInfo = lensRepository.findById(reviewBoardViewEntity.getLensId())
                .orElseThrow(()-> new NotFoundException(LENS_NOT_FOUND));

        return new ReviewBoardViewWithLensInfoEntity(reviewBoardViewEntity,lensInfo);
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
