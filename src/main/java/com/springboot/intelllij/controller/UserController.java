package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.AccountService;
import com.springboot.intelllij.services.FreeBoardService;
import com.springboot.intelllij.services.ReviewBoardPreviewService;
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
    @ResponseStatus(HttpStatus.OK)
    public CheckAvailableDTO checkId(@RequestParam(value = "id") String id) {
        return accountService.checkId(id);
    }

    @GetMapping(value = "/check/nickname")
    @ResponseStatus(HttpStatus.OK)
    public CheckAvailableDTO checkNickName(@RequestParam(value = "nickname") String nickName) {
        return accountService.checkNickName(nickName);
    }

    @GetMapping(value = "/check/phoneNum")
    @ResponseStatus(HttpStatus.OK)
    public CheckAvailableDTO checkPhoneNumber(@RequestParam(value = "phoneNum") String phoneNum) {
        return accountService.checkPhoneNumber(phoneNum);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody AccountEntity accountEntity) {
        return accountService.signup(accountEntity);
    }

    @PutMapping(value = "/modify/nickname")
    public ResponseEntity changeNickName(@RequestBody UserModifyDTO userModifyDTO) {
        return accountService.changeNickName(userModifyDTO);
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
    public List<FreeBoardEntity>  getMyFreeBoardPreviews() { return freeBoardService.getMyAllPreview(); }

    @GetMapping(value = "/review/me")
    public List<ReviewBoardViewWithLensInfoEntity> getMyReviewBoardPreview() { return reviewBoardPreviewService.getMyAllPreview(); }
}
