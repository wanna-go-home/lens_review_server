package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.services.LikePostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikePostCommentController {

    @Autowired
    LikePostCommentService likePostCommentService;

    @PostMapping(value =  RESTPath.FREE_BOARD + RESTPath.POST_LIKE)
    public ResponseEntity likeFreeBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.likeFreeboardPost(LikeableTables.FREE_BOARD, id);
    }

    @DeleteMapping(value = RESTPath.FREE_BOARD + RESTPath.POST_LIKE)
    public ResponseEntity unlikeFreeBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.unlikeFreeBoardPost(LikeableTables.FREE_BOARD, id);
    }

    @PostMapping(value = RESTPath.REVIEW_BOARD + RESTPath.POST_LIKE)
    public ResponseEntity likeReviewBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.likeReviewBoardPost(LikeableTables.REVIEW_BOARD, id);
    }

    @DeleteMapping(value = RESTPath.REVIEW_BOARD + RESTPath.POST_LIKE)
    public ResponseEntity unlikeReviewBoardPost(@PathVariable(name = "id") Integer id) {
        return likePostCommentService.unlikeReviewBoardPost(LikeableTables.REVIEW_BOARD, id);
    }

    @PostMapping(value = RESTPath.FREE_BOARD + RESTPath.COMMENT_LIKE)
    public ResponseEntity likeFreeBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.likeFreeBoardComment(LikeableTables.FREE_BOARD_COMMENT, id, commentId);
    }

    @DeleteMapping(value = RESTPath.FREE_BOARD + RESTPath.COMMENT_LIKE)
    public ResponseEntity unlikeFreeBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.unlikeFreeBoardComment(LikeableTables.FREE_BOARD_COMMENT, id, commentId);
    }

    @PostMapping(value = RESTPath.REVIEW_BOARD + RESTPath.COMMENT_LIKE)
    public ResponseEntity likeReviewBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.likeReviewBoardComment(LikeableTables.REVIEW_BOARD_COMMENT, id, commentId);
    }

    @DeleteMapping(value = RESTPath.REVIEW_BOARD + RESTPath.COMMENT_LIKE)
    public ResponseEntity unlikeReviewBoardComment(
            @PathVariable(name = "id") Integer id, @PathVariable(name = "commentId") Integer commentId) {
        return likePostCommentService.unlikeReviewBoardComment(LikeableTables.REVIEW_BOARD_COMMENT, id, commentId);
    }

}
