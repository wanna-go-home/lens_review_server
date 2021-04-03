package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.AuthConstant;
import com.springboot.intelllij.constant.CheckUserInfoEnum;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.AuthException;
import com.springboot.intelllij.exceptions.AuthExceptionEnum;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.*;
import com.springboot.intelllij.utils.CommentComparator;
import com.springboot.intelllij.utils.StringValidationUtils;
import com.springboot.intelllij.utils.TokenUtils;
import com.springboot.intelllij.utils.UserUtils;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.json.simple.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.springboot.intelllij.exceptions.AuthExceptionEnum.*;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.USER_NOT_FOUND;

@Service
public class AccountService {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    AccountRepository userRepo;

    @Autowired
    UserAuthRepository userAuthRepo;

    @Autowired
    UserRepository newUserRepo;

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

    public UserAuthDTO sendSMS(String phoneNum, String appHash) {
        Message smsMessage = new Message(smsApiKey, smsApiSecret);
        StringBuilder builder = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        String authCode = "";

        builder.append("<#> [");
        for(int i = 0; i < 6; i++) {
            authCode += String.valueOf(random.nextInt(10));
        }
        builder.append(authCode);
        builder.append("] 인증코드를 입력해주세요. ");
        builder.append(appHash);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", phoneNum);
        params.put("from",smsFrom);
        params.put("type","SMS");
        params.put("text",builder.toString());
        params.put("app_version", smsAppVersion);

        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setAuthCode(authCode);
        userAuthEntity.setPhoneNum(phoneNum);
        UserAuthEntity savedUserAuthEntity = userAuthRepo.save(userAuthEntity);

        try {
            JSONObject obj = (JSONObject) smsMessage.send(params);
            UserAuthDTO result = new UserAuthDTO();
            result.setRequestId(savedUserAuthEntity.getId());
            return result;
        } catch (CoolsmsException e) {
            throw new AuthException(SMS_SERVICE_NOT_WORKING);
        }
    }

    public ResponseEntity checkAuthCode(Integer requestId,String authCode) {
        UserAuthEntity userAuthEntity = userAuthRepo.findById(requestId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!userAuthEntity.getAuthCode().equals(authCode)) {
            throw new AuthException(AUTH_CODE_NOT_MATCH);
        } else if (ZonedDateTime.now().minusMinutes(3).isAfter(userAuthEntity.getCreatedAt())) {
            throw new AuthException(EXPIRED_REQUEST);
        } else {
            userAuthEntity.setVerified(true);
            userAuthRepo.save(userAuthEntity);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    public ResponseEntity signup(UserAuthDTO userAuthDTO) {
        UserAuthEntity userAuthEntity = userAuthRepo.findById(userAuthDTO.getRequestId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if(!userAuthEntity.isVerified()) {
            throw new AuthException(NOT_VERIFIED);
        }

        String token = "";
        List<NewAccountEntity> userList = newUserRepo.findByPhoneNum(userAuthEntity.getPhoneNum());
        if(userList.isEmpty()) {
            token = tokenUtils.generateJwt(createNewUser(userAuthDTO));
        } else {
            for (NewAccountEntity newAccountEntity : userList) {
                if(newAccountEntity.isActivate()) {
                    if(newAccountEntity.getPinNum().equals(userAuthDTO.getPin())) {
                        token = tokenUtils.generateJwt(newAccountEntity);
                        break;
                    } else {
                        newAccountEntity.setActivate(false);
                        newUserRepo.save(newAccountEntity);
                        token = tokenUtils.generateJwt(createNewUser(userAuthDTO));
                    }
                }
            }
        }

        if(token.equalsIgnoreCase("")) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(AuthConstant.AUTH_HEADER,AuthConstant.TOKEN_TYPE + " " + token);
        return ResponseEntity.ok().headers(responseHeaders).body(ResponseEntity.status(HttpStatus.OK).build());
    }

    private NewAccountEntity createNewUser(UserAuthDTO userAuthDTO) {
        UserAuthEntity userAuthEntity = userAuthRepo.findById(userAuthDTO.getRequestId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        NewAccountEntity newUser = new NewAccountEntity();
        newUser.setPhoneNum(userAuthEntity.getPhoneNum());
        newUser.setPinNum(userAuthDTO.getPin());
        newUser.setNickname(RandomStringUtils.randomAlphabetic(6));
        newUser.setActivate(true);
        return newUserRepo.save(newUser);
    }
}
