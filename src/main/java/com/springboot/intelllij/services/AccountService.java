package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.utils.StringValidationUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity deleteUser() {
        String user = UserUtils.getUserStringFromSecurityContextHolder();
        userRepo.deleteById(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity checkId(String id) {
        if (userRepo.findById(id).isPresent() || !StringValidationUtils.isValidEmail(id)) {
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

    public ResponseEntity checkPhoneNumber(String phoneNumber) {
        List<AccountEntity> phoneNumList = userRepo.findByPhoneNum(phoneNumber);
        if(phoneNumList.isEmpty() || !StringValidationUtils.isValidPhoneNumber(phoneNumber)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public ResponseEntity signup(AccountEntity accountEntity) {
        if(checkId(accountEntity.getAccountEmail()).getStatusCode().equals(HttpStatus.OK)
                && checkNickName(accountEntity.getNickname()).getStatusCode().equals(HttpStatus.OK)
                && checkPhoneNumber(accountEntity.getPhoneNum()).getStatusCode().equals(HttpStatus.OK)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            accountEntity.setAccountPw(bCryptPasswordEncoder.encode(accountEntity.getAccountPw()));
            userRepo.save(accountEntity);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public UserInfoDTO getUserInfo() {
        UserInfoDTO userInfo = new UserInfoDTO();
        AccountEntity account = UserUtils.getUserEntity();
        String user = account.getAccountEmail();

        List<ReviewBoardEntity> reviewBoardEntities = reviewRepo.findByEmail(user);
        List<FreeBoardEntity> freeBoardEntities = freeRepo.findByEmail(user);
        List<ReviewBoardCommentEntity> reviewBoardCommentEntities = reviewCOmmentRepo.findByEmail(user);
        List<FreeBoardCommentEntity> freeBoardCommentEntities = freeCommentRepo.findByEmail(user);

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
        userInfo.setNickName(account.getNickname());

        return userInfo;
    }

}
