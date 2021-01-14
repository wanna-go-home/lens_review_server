package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import com.springboot.intelllij.domain.ReviewBoardViewWithLensInfoEntity;
import com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.LensRepository;
import com.springboot.intelllij.repository.ReviewBoardPreviewRepository;
import com.springboot.intelllij.utils.BoardComparator;
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

    public List<ReviewBoardViewEntity> getAllPreview() {
        List<ReviewBoardViewEntity> result = reviewBoardPreviewRepo.findAll();

        result.sort(new BoardComparator());

        return result;
    }

    public List<ReviewBoardViewWithLensInfoEntity> getMyAllPreview() {
        Integer accountId = (Integer)SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<ReviewBoardViewWithLensInfoEntity> result = new ArrayList<>();

        result.addAll(reviewBoardPreviewRepo.findByAccountId(accountId).stream()
                .map(reviewBoardViewEntity -> new ReviewBoardViewWithLensInfoEntity(reviewBoardViewEntity,
                        lensRepo.findById(reviewBoardViewEntity.getLensId()).orElseThrow(() -> new NotFoundException(LENS_NOT_FOUND))))
                .collect(Collectors.toList()));
        result.sort(new BoardComparator());

        return result;
    }
}
