package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.utils.BoardComparator;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.*;

@Service
public class ReviewBoardService {

    @Autowired
    private ReviewBoardRepository reviewBoardRepo;
    @Autowired
    private LensPreviewRepository lensRepository;
    @Autowired
    private ReviewBoardCommentRepository reviewBoardCommentRepository;

    public List<ReviewBoardWithLensInfoEntity> getAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<ReviewBoardWithLensInfoEntity> result = reviewBoardRepo.findAll().stream()
                .map(reviewBoardViewEntity -> new ReviewBoardWithLensInfoEntity(reviewBoardViewEntity,
                        lensRepository.findById(reviewBoardViewEntity.getLensId()).orElseThrow(() -> new NotFoundException(LENS_NOT_FOUND))))
                .sorted(new BoardComparator())
                .collect(Collectors.toList());
        result = EntityUtils.setIsLiked(result, accountId, LikeableTables.REVIEW_BOARD);
        return (List<ReviewBoardWithLensInfoEntity>)EntityUtils.setIsAuthor(result, accountId);
    }

    public List<ReviewBoardWithLensInfoEntity> getMyAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<ReviewBoardWithLensInfoEntity> result = reviewBoardRepo.findByAccountId(accountId).stream()
                .map(reviewBoardViewEntity -> new ReviewBoardWithLensInfoEntity(reviewBoardViewEntity,
                        lensRepository.findById(reviewBoardViewEntity.getLensId()).orElseThrow(() -> new NotFoundException(LENS_NOT_FOUND))))
                .sorted(new BoardComparator())
                .collect(Collectors.toList());

        result = EntityUtils.setIsLiked(result, accountId, LikeableTables.REVIEW_BOARD);
        return (List<ReviewBoardWithLensInfoEntity>)EntityUtils.setIsAuthor(result, accountId);
    }

    @Transactional
    public ReviewBoardWithLensInfoEntity getReviewBoardById(Integer id) {
        ReviewBoardEntity reviewBoardEntity = reviewBoardRepo.findById(id).
                orElseThrow(()-> new NotFoundException(BOARD_NOT_FOUND));
        reviewBoardEntity.increaseReplyCnt();
        reviewBoardEntity = reviewBoardRepo.save(reviewBoardEntity);
        LensPreviewEntity lensInfo = lensRepository.findById(reviewBoardEntity.getLensId())
                .orElseThrow(()-> new NotFoundException(LENS_NOT_FOUND));
        ReviewBoardWithLensInfoEntity reviewWithLensInfo =
                new ReviewBoardWithLensInfoEntity(reviewBoardEntity, lensInfo);
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        reviewWithLensInfo = EntityUtils.setIsLiked(reviewWithLensInfo, accountId, LikeableTables.REVIEW_BOARD, id);
        return EntityUtils.setIsAuthor(reviewWithLensInfo, accountId);
    }

    @Transactional
    public List<ReviewBoardEntity> getReviewBoardByLensId(Integer lensId) {
        List<ReviewBoardEntity> reviewBoardEntityList = reviewBoardRepo.findByLensId(lensId);
        reviewBoardEntityList.forEach(reviewBoardEntity -> {
            reviewBoardEntity.increaseViewCnt();
            reviewBoardRepo.save(reviewBoardEntity);
        });
        return reviewBoardEntityList;
    }

    public ResponseEntity addPostToReviewBoard(ReviewBoardDto reviewBoardDto) {
        Integer accountId = (Integer)SecurityContextHolder.getContext().getAuthentication().getDetails();
        ReviewBoardEntity reviewBoard = new ReviewBoardEntity(reviewBoardDto, accountId);
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
