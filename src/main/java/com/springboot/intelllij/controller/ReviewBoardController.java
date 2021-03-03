package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.ReviewBoardCommentService;
import com.springboot.intelllij.services.ReviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewBoardWithLensInfoEntity> getReviewBoardPreview() {
        return reviewBoardService.getAllPreview();
    }

    @GetMapping(value = RESTPath.ID)
    @ResponseStatus(HttpStatus.OK)
    public ReviewBoardWithLensInfoEntity getReviewBoardById(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.getReviewBoardById(id);
    }

    @PutMapping(value = RESTPath.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateReviewBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        reviewBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = RESTPath.ID)
    @ResponseStatus(HttpStatus.OK)
    public void deletePostAndComments(@PathVariable(name = "id") Integer id) {
        reviewBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addPostToReviewBoard(@RequestBody ReviewBoardDto reviewBoardDto) {
        reviewBoardService.addPostToReviewBoard(reviewBoardDto);
    }

    @PostMapping(value = RESTPath.COMMENTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody CommentInputDTO comment) {
        reviewBoardCommentService.post(id, comment);
    }

    @GetMapping(value = RESTPath.COMMENTS)
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDTO> getReviewBoardComment(@PathVariable(name = "id") Integer postId) {
        return reviewBoardCommentService.getCommentByPostId(postId);
    }

    @GetMapping(value = RESTPath.COMMENT_ID)
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDTO> getCommentByPostIdAndCommentId(
            @PathVariable(name = "id") Integer postId, @PathVariable(name = "commentId") Integer commentId) {
        return reviewBoardCommentService.getAllCommentByPostId(postId, commentId);
    }

    @PutMapping(value = RESTPath.COMMENT_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReviewBoardCommentEntity updateReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId,
            @RequestBody CommentInputDTO comment) {
        return reviewBoardCommentService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping(value = RESTPath.COMMENT_ID)
    @ResponseStatus(HttpStatus.OK)
    public void deleteReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId) {
        reviewBoardCommentService.deleteComment(postId, commentId);
    }

}
