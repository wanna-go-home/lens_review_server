package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.FreeBoardCommentService;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.FREE_BOARD)
public class FreeBoardController {

    @Autowired
    FreeBoardService freeBoardService;

    @Autowired
    FreeBoardCommentService freeBoardCommentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FreeBoardEntity>  getFreeBoardPreviews() { return freeBoardService.getAllPreview(); }

    @GetMapping(value = RESTPath.ID)
    @ResponseStatus(HttpStatus.OK)
    public FreeBoardEntity getFreeBoardById(@PathVariable(name = "id") Integer id) {
        return freeBoardService.getFreeBoardById(id);
    }

    @PutMapping(value = RESTPath.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateFreeBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        freeBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = RESTPath.ID)
    @ResponseStatus(HttpStatus.OK)
    public void deletePostAndComments(@PathVariable(name = "id") Integer id) {
        freeBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addPostToFreeBoard(@RequestBody BoardUpdateDTO freeBoard) {
        freeBoardService.addPostToFreeBoard(freeBoard);
    }

    @PostMapping(value = RESTPath.COMMENTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody CommentInputDTO comment) {
        freeBoardCommentService.post(id, comment);
    }

    @GetMapping(value = RESTPath.COMMENTS)
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDTO> getFreeBoardComment(@PathVariable(name = "id") Integer postId) {
        return freeBoardCommentService.getCommentByPostId(postId);
    }

    @GetMapping(value = RESTPath.COMMENT_ID)
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDTO> getFreeBoardAllComments(
            @PathVariable(name = "id") Integer postId, @PathVariable(name = "commentId") Integer commentId) {
        return freeBoardCommentService.getAllCommentByPostId(postId,commentId);
    }

    @PutMapping(value = RESTPath.COMMENT_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public FreeBoardCommentEntity updateFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId,
            @RequestBody CommentInputDTO comment) {
        return freeBoardCommentService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping(value = RESTPath.COMMENT_ID)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId) {
        freeBoardCommentService.deleteComment(postId, commentId);
    }

}
