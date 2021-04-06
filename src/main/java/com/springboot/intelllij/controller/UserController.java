package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.AccountService;
import com.springboot.intelllij.services.FreeBoardService;
import com.springboot.intelllij.services.ReviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.USER)
public class UserController {

    @Autowired
    AccountService accountService;

    @Autowired
    FreeBoardService freeBoardService;

    @Autowired
    ReviewBoardService reviewBoardService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountEntity> getAllUsers() {
        return accountService.getAllUsers();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser() {
        accountService.deleteUser();
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody UserAuthDTO userAuthDTO) {
        return accountService.signup(userAuthDTO);
    }

    @PutMapping(value = "/modify/nickname")
    public ResponseEntity changeNickName(@RequestBody UserModifyDTO userModifyDTO) {
        return accountService.changeNickName(userModifyDTO);
    }

    @GetMapping(value = "/me")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO userInfo() {
        return accountService.getUserInfo();
    }

    @GetMapping(value = "/article/comments/me")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDTO> userArticleComments() {
        return accountService.getUserArticleComments();
    }

    @GetMapping(value = "/review/comments/me")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDTO> userReviewComments() {
        return accountService.getUserReviewComments();
    }

    @GetMapping(value = "/comments/me")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDTO> getUserAllComments() {
        return accountService.getUserAllComments();
    }

    @GetMapping(value = "/article/me")
    @ResponseStatus(HttpStatus.OK)
    public List<FreeBoardEntity>  getMyFreeBoardPreviews() { return freeBoardService.getMyAllPreview(); }

    @GetMapping(value = "/review/me")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewBoardWithLensInfoEntity> getMyReviewBoardPreview() { return reviewBoardService.getMyAllPreview(); }

    @GetMapping(value = "/check/sendsms")
    public UserAuthDTO sendSMS(@RequestParam(value = "phoneNum") String phoneNum,
                                  @RequestParam(value = "appHash", defaultValue = "") String appHash) {
        return accountService.sendSMS(phoneNum, appHash);
    }

    @GetMapping(value = "/check/authcode")
    public ResponseEntity checkAuthCode(@RequestParam(value = "requestId") Integer requestId,
                                     @RequestParam(value = "authCode", defaultValue = "") String authCode) {
        return accountService.checkAuthCode(requestId, authCode);
    }
}
