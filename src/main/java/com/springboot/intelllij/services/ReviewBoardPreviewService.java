package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import com.springboot.intelllij.repository.ReviewBoardPreviewRepository;
import com.springboot.intelllij.utils.BoardComparator;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
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

        return (List<ReviewBoardViewEntity>)EntityUtils.setIsAuthor(result, UserUtils.getUserIdFromSecurityContextHolder());
    }

    public List<ReviewBoardViewEntity> getMyAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<ReviewBoardViewEntity> result = reviewBoardPreviewRepo.findByAccountId(accountId);
        result.sort(new BoardComparator());

        return (List<ReviewBoardViewEntity>)EntityUtils.setIsAuthor(result, accountId);
    }
}
