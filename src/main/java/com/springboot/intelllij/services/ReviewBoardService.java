package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.domain.ReviewBoardPreview;
import com.springboot.intelllij.repository.ReviewBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewBoardService {

    @Autowired
    private ReviewBoardRepository reviewBoardRepo;

    public List<ReviewBoardEntity> getAllPosts() { return reviewBoardRepo.findAll(); }

    public ResponseEntity addPostToReviewBoard(@RequestBody ReviewBoardEntity reviewBoard) {
        reviewBoardRepo.save(reviewBoard);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public List<ReviewBoardPreview> getAllPreviews() {
        int previewRange = 100;
        List<ReviewBoardPreview> previews = new ArrayList<>();
        List<ReviewBoardEntity> allPosts = reviewBoardRepo.findAll();
        for(ReviewBoardEntity post : allPosts) {
            String content = post.getContent();
            String shortContent = content.substring(0, Math.min(content.length(), previewRange));
            previews.add(new ReviewBoardPreview(post.getId(), post.getUserId(), post.getTitle(), shortContent));
        }
        return previews;
    }
}
