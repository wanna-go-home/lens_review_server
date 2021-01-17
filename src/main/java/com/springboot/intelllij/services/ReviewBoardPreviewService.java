package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import com.springboot.intelllij.domain.ReviewBoardViewWithLensInfoEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.LensRepository;
import com.springboot.intelllij.repository.ReviewBoardPreviewRepository;
import com.springboot.intelllij.utils.BoardComparator;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.LENS_NOT_FOUND;

@Service
public class ReviewBoardPreviewService {

    @Autowired
    ReviewBoardPreviewRepository reviewBoardPreviewRepo;

    @Autowired
    LensRepository lensRepo;

    @Autowired
    EntityUtils entityUtils;

    public List<ReviewBoardViewEntity> getAllPreview() {
        List<ReviewBoardViewEntity> result = reviewBoardPreviewRepo.findAll();
        result.sort(new BoardComparator());
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        result = entityUtils.setIsLiked(result, accountId, LikeableTables.REVIEW_BOARD);
        return (List<ReviewBoardViewEntity>)EntityUtils.setIsAuthor(result, UserUtils.getUserIdFromSecurityContextHolder());
    }

    public List<ReviewBoardViewWithLensInfoEntity> getMyAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<ReviewBoardViewWithLensInfoEntity> result = reviewBoardPreviewRepo.findByAccountId(accountId).stream()
                .map(reviewBoardViewEntity -> new ReviewBoardViewWithLensInfoEntity(reviewBoardViewEntity,
                        lensRepo.findById(reviewBoardViewEntity.getLensId()).orElseThrow(() -> new NotFoundException(LENS_NOT_FOUND))))
                .sorted(new BoardComparator())
                .collect(Collectors.toList());
        result = entityUtils.setIsLiked(result, accountId, LikeableTables.REVIEW_BOARD);
        return (List<ReviewBoardViewWithLensInfoEntity>)EntityUtils.setIsAuthor(result, accountId);
    }
}
