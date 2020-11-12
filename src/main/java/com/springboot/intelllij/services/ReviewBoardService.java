package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardDto;
import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.ReviewBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.REVIEW_BOARD_NOT_FOUND;

@Service
public class ReviewBoardService {

    @Autowired
    private ReviewBoardRepository reviewBoardRepo;

    public List<ReviewBoardEntity> getAllPosts() { return reviewBoardRepo.findAll(); }

    public ReviewBoardEntity getReviewBoardById(Integer id) {
        return reviewBoardRepo.findById(id).orElseThrow(()-> new NotFoundException(REVIEW_BOARD_NOT_FOUND));
    }

    public ResponseEntity addPostToReviewBoard(ReviewBoardDto reviewBoardDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReviewBoardEntity reviewBoard = new ReviewBoardEntity(reviewBoardDto,principal.toString());
        reviewBoardRepo.save(reviewBoard);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
