package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.ReviewBoardCommentService;
import com.springboot.intelllij.services.ReviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.REVIEW_BOARD)
public class ReviewBoardController {

    @Autowired
    ReviewBoardService reviewBoardService;

    @Autowired
    ReviewBoardCommentService reviewBoardCommentService;

    @GetMapping
    public List<ReviewBoardWithLensInfoEntity> getReviewBoardPreview() {
        return reviewBoardService.getAllPreview();
    }

    @GetMapping(value = RESTPath.ID)
    public ReviewBoardWithLensInfoEntity getReviewBoardById(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.getReviewBoardById(id);
    }

    @PutMapping(value = RESTPath.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateReviewBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        return reviewBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = RESTPath.ID)
    public ResponseEntity deletePostAndComments(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPostToReviewBoard(@RequestBody ReviewBoardDto reviewBoardDto) {
        return reviewBoardService.addPostToReviewBoard(reviewBoardDto);
    }

    @PostMapping(value = RESTPath.COMMENTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody CommentInputDTO comment) {
        return reviewBoardCommentService.post(id, comment);
    }

    @GetMapping(value = RESTPath.COMMENTS)
    public List<CommentOutputDTO> getReviewBoardComment(@PathVariable(name = "id") Integer postId) {
        return reviewBoardCommentService.getCommentByPostId(postId);
    }

    @GetMapping(value = RESTPath.COMMENT_ID)
    public List<CommentOutputDTO> getCommentByPostIdAndCommentId(
            @PathVariable(name = "id") Integer postId, @PathVariable(name = "commentId") Integer commentId) {
        return reviewBoardCommentService.getAllCommentByPostId(postId, commentId);
    }

    @PutMapping(value = RESTPath.COMMENT_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReviewBoardCommentEntity updateReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId,
            @RequestBody CommentInputDTO comment) {
        return reviewBoardCommentService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping(value = RESTPath.COMMENT_ID)
    public ResponseEntity deleteReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId) {
        return reviewBoardCommentService.deleteComment(postId, commentId);
    }

}
