package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardPreviewEntity;
import com.springboot.intelllij.repository.ReviewBoardPreviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewBoardPreviewService {

    @Autowired
    ReviewBoardPreviewRepository reviewBoardPreviewRepo;

    public List<ReviewBoardPreviewEntity> getAllPreview() { return reviewBoardPreviewRepo.findAll(); }
}
