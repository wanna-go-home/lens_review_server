package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.CommentOutputDTO;
import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.ReviewBoardWithLensInfoEntity;
import com.springboot.intelllij.services.LikePostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikePostCommentController {

    @Autowired
    LikePostCommentService likePostCommentService;

    @PostMapping(value =  RESTPath.FREE_BOARD + RESTPath.POST_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public FreeBoardEntity likeFreeBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.likeFreeboardPost(LikeableTables.FREE_BOARD, id);
    }

    @DeleteMapping(value = RESTPath.FREE_BOARD + RESTPath.POST_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public FreeBoardEntity unlikeFreeBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.unlikeFreeBoardPost(LikeableTables.FREE_BOARD, id);
    }

    @PostMapping(value = RESTPath.REVIEW_BOARD + RESTPath.POST_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReviewBoardWithLensInfoEntity likeReviewBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.likeReviewBoardPost(LikeableTables.REVIEW_BOARD, id);
    }

    @DeleteMapping(value = RESTPath.REVIEW_BOARD + RESTPath.POST_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReviewBoardWithLensInfoEntity unlikeReviewBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.unlikeReviewBoardPost(LikeableTables.REVIEW_BOARD, id);
    }

    @PostMapping(value = RESTPath.FREE_BOARD + RESTPath.COMMENT_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentOutputDTO likeFreeBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.likeFreeBoardComment(LikeableTables.FREE_BOARD_COMMENT, id, commentId);
    }

    @DeleteMapping(value = RESTPath.FREE_BOARD + RESTPath.COMMENT_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentOutputDTO unlikeFreeBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.unlikeFreeBoardComment(LikeableTables.FREE_BOARD_COMMENT, id, commentId);
    }

    @PostMapping(value = RESTPath.REVIEW_BOARD + RESTPath.COMMENT_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentOutputDTO likeReviewBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.likeReviewBoardComment(LikeableTables.REVIEW_BOARD_COMMENT, id, commentId);
    }

    @DeleteMapping(value = RESTPath.REVIEW_BOARD + RESTPath.COMMENT_LIKE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentOutputDTO unlikeReviewBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.unlikeReviewBoardComment(LikeableTables.REVIEW_BOARD_COMMENT, id, commentId);
    }

}
