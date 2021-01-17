package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.AccountService;
import com.springboot.intelllij.services.FreeBoardPreviewService;
import com.springboot.intelllij.services.ReviewBoardPreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.USER)
public class UserController {

    @Autowired
    AccountService accountService;

    @Autowired
    FreeBoardPreviewService freeBoardPreviewService;

    @Autowired
    ReviewBoardPreviewService reviewBoardPreviewService;

    @GetMapping
    public List<AccountEntity> getAllUsers() {
        return accountService.getAllUsers();
    }

    @DeleteMapping
    public ResponseEntity deleteUser() {
        return accountService.deleteUser();
    }

    @GetMapping(value = "/check/id")
    public ResponseEntity checkId(@RequestParam(value = "id") String id) {
        return accountService.checkId(id);
    }

    @GetMapping(value = "/check/nickname")
    public ResponseEntity checkNickName(@RequestParam(value = "nickname") String nickName) {
        return accountService.checkNickName(nickName);
    }

    @GetMapping(value = "/check/phoneNum")
    public ResponseEntity checkPhoneNumber(@RequestParam(value = "phoneNum") String phoneNum) {
        return accountService.checkPhoneNumber(phoneNum);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody AccountEntity accountEntity) {
        return accountService.signup(accountEntity);
    }

    @PutMapping(value = "/modify/nickname")
    public ResponseEntity changeNickName(@RequestParam(value = "nickname") String nickName) {
        return accountService.changeNickName(nickName);
    }

    @GetMapping(value = "/me")
    public UserInfoDTO userInfo() {
        return accountService.getUserInfo();
    }

    @GetMapping(value = "/article/comments/me")
    public List<CommentOutputDTO> userArticleComments() {
        return accountService.getUserArticleComments();
    }

    @GetMapping(value = "/review/comments/me")
    public List<CommentOutputDTO> userReviewComments() {
        return accountService.getUserReviewComments();
    }

    @GetMapping(value = "/comments/me")
    public List<CommentOutputDTO> getUserAllComments() {
        return accountService.getUserAllComments();
    }

    @GetMapping(value = "/article/me")
    public List<FreeBoardViewEntity>  getMyFreeBoardPreviews() { return freeBoardPreviewService.getMyAllPreview(); }

    @GetMapping(value = "/review/me")
    public List<ReviewBoardViewWithLensInfoEntity> getMyReviewBoardPreview() { return reviewBoardPreviewService.getMyAllPreview(); }
}
