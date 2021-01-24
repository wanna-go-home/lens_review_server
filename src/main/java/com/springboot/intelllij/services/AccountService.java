package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.utils.CommentComparator;
import com.springboot.intelllij.utils.StringValidationUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    ReviewBoardCommentRepository reviewCommentRepo;

    public List<AccountEntity> getAllUsers() { return userRepo.findAll(); }

    public ResponseEntity deleteUser() {
        AccountEntity accountEntity = UserUtils.getUserEntity();

        accountEntity.setAccountEmail(null);
        accountEntity.setAccountPw(null);
        accountEntity.setPhoneNum(null);
        accountEntity.setActive(false);
        userRepo.save(accountEntity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity checkId(String email) {
        if (userRepo.findByAccountEmail(email).isPresent() || !StringValidationUtils.isValidEmail(email)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity checkNickName(String nickName) {
        if (isDuplicatedNickName(nickName)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public ResponseEntity changeNickName(UserModifyDTO userModifyDTO) {
        if(isDuplicatedNickName(userModifyDTO.getNickName())) {
            AccountEntity user = UserUtils.getUserEntity();
            user.setNickname(userModifyDTO.getNickName());
            userRepo.save(user);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    private boolean isDuplicatedNickName(String nickName) {
        List<AccountEntity> nickNameList = userRepo.findByNickname(nickName);
        if(nickNameList.isEmpty()) {
            return true;
        }
        return false;
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
        int accountId = account.getId();

        List<ReviewBoardEntity> reviewBoardEntities = reviewRepo.findByAccountId(accountId);
        List<FreeBoardEntity> freeBoardEntities = freeRepo.findByAccountId(accountId);
        List<ReviewBoardCommentEntity> reviewBoardCommentEntities = reviewCommentRepo.findByAccountId(accountId);
        List<FreeBoardCommentEntity> freeBoardCommentEntities = freeCommentRepo.findByAccountId(accountId);

        int likeCount = 0;
        likeCount += reviewBoardEntities.stream().mapToInt(reviewBoardEntity -> reviewBoardEntity.getLikeCnt()).sum();
        likeCount += freeBoardEntities.stream().mapToInt(freeBoardEntity -> freeBoardEntity.getLikeCnt()).sum();
        likeCount += reviewBoardCommentEntities.stream().mapToInt(reviewComment -> reviewComment.getLikeCnt()).sum();
        likeCount += freeBoardCommentEntities.stream().mapToInt(freeComment -> freeComment.getLikeCnt()).sum();

        userInfo.setAccountId(accountId);
        userInfo.setEmail(account.getAccountEmail());
        userInfo.setFreeCount(freeBoardEntities.size());
        userInfo.setReviewCount(reviewBoardEntities.size());
        userInfo.setFreeCommentCount(freeBoardCommentEntities.size());
        userInfo.setReviewCommentCount(reviewBoardCommentEntities.size());
        userInfo.setLikeCount(likeCount);
        userInfo.setNickName(account.getNickname());

        return userInfo;
    }

    public List<CommentOutputDTO> getUserArticleComments() {
        AccountEntity user = UserUtils.getUserEntity();
        List<CommentOutputDTO> result = new ArrayList<>();
        List<FreeBoardCommentEntity> freeboardComments = freeCommentRepo.findByAccountId(user.getId());

        result.addAll(freeboardComments.stream()
                .map(freeBoardCommentEntity -> new CommentOutputDTO(freeBoardCommentEntity, user.getNickname()))
                .collect(Collectors.toList()));
        result.sort(new CommentComparator());

        return result;
    }

    public List<CommentOutputDTO> getUserReviewComments() {
        AccountEntity user = UserUtils.getUserEntity();
        List<CommentOutputDTO> result = new ArrayList<>();
        List<ReviewBoardCommentEntity> reviewBoarcComments = reviewCommentRepo.findByAccountId(user.getId());

        result.addAll(reviewBoarcComments.stream()
                .map(reviewBoardCommentEntity -> new CommentOutputDTO(reviewBoardCommentEntity, user.getNickname()))
                .collect(Collectors.toList()));
        result.sort(new CommentComparator());

        return result;
    }

    public List<CommentOutputDTO> getUserAllComments() {
        AccountEntity user = UserUtils.getUserEntity();
        List<CommentOutputDTO> result = new ArrayList<>();
        List<ReviewBoardCommentEntity> reviewBoarcComments = reviewCommentRepo.findByAccountId(user.getId());
        List<FreeBoardCommentEntity> freeboardComments = freeCommentRepo.findByAccountId(user.getId());

        result.addAll(freeboardComments.stream()
                .map(freeBoardCommentEntity -> new CommentOutputDTO(freeBoardCommentEntity, user.getNickname()))
                .collect(Collectors.toList()));
        result.addAll(reviewBoarcComments.stream()
                .map(reviewBoardCommentEntity -> new CommentOutputDTO(reviewBoardCommentEntity, user.getNickname()))
                .collect(Collectors.toList()));
        result.sort(new CommentComparator());

        return result;
    }

}
