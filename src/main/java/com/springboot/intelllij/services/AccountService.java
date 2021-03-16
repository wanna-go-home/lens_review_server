package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.CheckUserInfoEnum;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.utils.CommentComparator;
import com.springboot.intelllij.utils.StringValidationUtils;
import com.springboot.intelllij.utils.UserUtils;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.json.simple.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
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

    @Value("${sms.api-key}")
    String smsApiKey;

    @Value("${sms.api-secret}")
    String smsApiSecret;

    @Value("${sms.app-version}")
    String smsAppVersion;

    @Value("${sms.from}")
    String smsFrom;


    public List<AccountEntity> getAllUsers() { return userRepo.findAll(); }

    public void deleteUser() {
        AccountEntity accountEntity = UserUtils.getUserEntity();

        accountEntity.setAccountEmail(null);
        accountEntity.setAccountPw(null);
        accountEntity.setPhoneNum(null);
        accountEntity.setActive(false);
        userRepo.save(accountEntity);
    }

    public CheckAvailableDTO checkId(String email) {
        CheckAvailableDTO checkAvailable;
        if (userRepo.findByAccountEmail(email).isPresent()) {
            checkAvailable = new CheckAvailableDTO(false, CheckUserInfoEnum.OCCUPIED.ordinal());
        } else if (!StringValidationUtils.isValidEmail(email)) {
            checkAvailable = new CheckAvailableDTO(false, CheckUserInfoEnum.INVALID.ordinal());
        } else {
            checkAvailable = new CheckAvailableDTO(true, CheckUserInfoEnum.AVAILABLE.ordinal());
        }
        return checkAvailable;
    }

    public CheckAvailableDTO checkNickName(String nickName) {
        CheckAvailableDTO checkAvailable;
        if (isDuplicatedNickName(nickName)) {
            checkAvailable = new CheckAvailableDTO(false, CheckUserInfoEnum.OCCUPIED.ordinal());
        } else {
            checkAvailable = new CheckAvailableDTO(true, CheckUserInfoEnum.AVAILABLE.ordinal());
        }
        return checkAvailable;
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
        return !nickNameList.isEmpty();
    }

    public CheckAvailableDTO checkPhoneNumber(String phoneNumber) {
        CheckAvailableDTO checkAvailable;
        List<AccountEntity> phoneNumList = userRepo.findByPhoneNum(phoneNumber);
        if(!phoneNumList.isEmpty()) {
            checkAvailable = new CheckAvailableDTO(false, CheckUserInfoEnum.OCCUPIED.ordinal());
        } else if (!StringValidationUtils.isValidPhoneNumber(phoneNumber)) {
            checkAvailable = new CheckAvailableDTO(false, CheckUserInfoEnum.INVALID.ordinal());
        } else {
            checkAvailable = new CheckAvailableDTO(true, CheckUserInfoEnum.AVAILABLE.ordinal());
        }
        return checkAvailable;
    }

    public ResponseEntity<CheckAvailableDTO> signup(AccountEntity accountEntity) {
        CheckAvailableDTO checkAvailable;
        checkAvailable = checkId(accountEntity.getAccountEmail());
        if (!checkAvailable.isAvailable()) {
            return new ResponseEntity<CheckAvailableDTO>(checkAvailable, HttpStatus.NOT_ACCEPTABLE);
        }
        checkAvailable = checkNickName(accountEntity.getNickname());
        if (!checkAvailable.isAvailable()) {
            return new ResponseEntity<CheckAvailableDTO>(checkAvailable, HttpStatus.NOT_ACCEPTABLE);
        }
        checkAvailable = checkPhoneNumber(accountEntity.getPhoneNum());
        if (!checkAvailable.isAvailable()) {
            return new ResponseEntity<CheckAvailableDTO>(checkAvailable, HttpStatus.NOT_ACCEPTABLE);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        accountEntity.setAccountPw(bCryptPasswordEncoder.encode(accountEntity.getAccountPw()));
        userRepo.save(accountEntity);
        return ResponseEntity.status(HttpStatus.OK).build();
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

    public ResponseEntity sendSMS(String requestId, String phoneNum, String appHash) {
        Message smsMessage = new Message(smsApiKey, smsApiSecret);
        StringBuilder builder = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());

        builder.append("<#> [");
        for(int i = 0; i < 6; i++) {
            builder.append(random.nextInt(10));
        }

        builder.append("] 인증코드를 입력해주세요. ");
        builder.append(appHash);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", phoneNum);
        params.put("from",smsFrom);
        params.put("type","SMS");
        params.put("text",builder.toString());
        params.put("app_version", smsAppVersion);

        try {
            JSONObject obj = (JSONObject) smsMessage.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
