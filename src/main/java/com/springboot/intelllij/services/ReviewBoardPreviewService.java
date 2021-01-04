package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import com.springboot.intelllij.repository.ReviewBoardPreviewRepository;
import com.springboot.intelllij.utils.BoardComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewBoardPreviewService {

    @Autowired
    ReviewBoardPreviewRepository reviewBoardPreviewRepo;

    public List<ReviewBoardViewEntity> getAllPreview() {
        List<ReviewBoardViewEntity> result = reviewBoardPreviewRepo.findAll();

        result.sort(new BoardComparator());

        return result;
    }

    public List<ReviewBoardViewEntity> getMyAllPreview() {
        Integer accountId = (Integer)SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<ReviewBoardViewEntity> result = reviewBoardPreviewRepo.findByAccountId(accountId);

        result.sort(new BoardComparator());

        return result;
    }
}
