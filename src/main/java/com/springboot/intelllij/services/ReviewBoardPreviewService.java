package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import com.springboot.intelllij.repository.ReviewBoardPreviewRepository;
import com.springboot.intelllij.utils.BoardComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user = principal.toString();
        List<ReviewBoardViewEntity> result = reviewBoardPreviewRepo.findByEmail(user);

        result.sort(new BoardComparator());

        return result;
    }
}
