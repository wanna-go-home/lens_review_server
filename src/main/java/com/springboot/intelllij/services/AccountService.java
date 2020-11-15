package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.REVIEW_BOARD_NOT_FOUND;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.USER_NOT_FOUND;

@Service
public class AccountService {

    @Autowired
    AccountRepository userRepo;

    @Autowired
    ReviewBoardRepository reviewRepo;

    @Autowired
    FreeBoardRepository freeRepo;

    @Autowired
    FreeBoardCommentRepository freeCommentRepo;

    @Autowired
    ReviewBoardCommentRepository reviewCOmmentRepo;

    public List<AccountEntity> getAllUsers() { return userRepo.findAll(); }

    public ResponseEntity checkId(String id) {
        if (userRepo.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity checkNickName(String nickName) {
        List<AccountEntity> nickNameList = userRepo.findByNickname(nickName);
        if(nickNameList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public ResponseEntity signup(AccountEntity accountEntity) {
        if(checkId(accountEntity.getAccount()).getStatusCode().equals(HttpStatus.OK)
                && checkNickName(accountEntity.getNickname()).getStatusCode().equals(HttpStatus.OK)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            accountEntity.setAccountPw(bCryptPasswordEncoder.encode(accountEntity.getAccountPw()));
            userRepo.save(accountEntity);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public UserInfoDTO getUserInfo() {
        UserInfoDTO userInfo = new UserInfoDTO();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user = principal.toString();
        AccountEntity account = userRepo.findById(user).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        List<ReviewBoardEntity> reviewBoardEntities = reviewRepo.findByAccount(user);
        List<FreeBoardEntity> freeBoardEntities = freeRepo.findByAccount(user);
        List<ReviewBoardCommentEntity> reviewBoardCommentEntities = reviewCOmmentRepo.findByAccountId(user);
        List<FreeBoardCommentEntity> freeBoardCommentEntities = freeCommentRepo.findByAccountId(user);

        int likeCount = 0;
        likeCount += reviewBoardEntities.stream().mapToInt(reviewBoardEntity -> reviewBoardEntity.getLikeCnt()).sum();
        likeCount += freeBoardEntities.stream().mapToInt(freeBoardEntity -> freeBoardEntity.getLikeCnt()).sum();
        likeCount += reviewBoardCommentEntities.stream().mapToInt(reviewComment -> reviewComment.getLikeCnt()).sum();
        likeCount += freeBoardCommentEntities.stream().mapToInt(freeComment -> freeComment.getLikeCnt()).sum();

        userInfo.setFreeCount(freeBoardEntities.size());
        userInfo.setReviewCount(reviewBoardEntities.size());
        userInfo.setFreeCommentCount(freeBoardCommentEntities.size());
        userInfo.setReviewCommentCount(reviewBoardCommentEntities.size());
        userInfo.setLikeCount(likeCount);
        userInfo.setEmail(account.getEmail());
        userInfo.setNickName(account.getNickname());

        return userInfo;
    }

}
