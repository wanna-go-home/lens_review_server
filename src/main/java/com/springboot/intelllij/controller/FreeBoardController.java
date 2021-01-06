package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.FreeBoardCommentService;
import com.springboot.intelllij.services.FreeBoardPreviewService;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
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
    FreeBoardPreviewService freeBoardPreviewService;

    @Autowired
    FreeBoardCommentService freeBoardCommentService;

    @GetMapping
    public List<FreeBoardViewEntity>  getFreeBoardPreviews() { return freeBoardPreviewService.getAllPreview(); }

    @GetMapping(value = "/{id}")
    public FreeBoardViewEntity getFreeBoardById(@PathVariable(name = "id") Integer id) {
        return freeBoardService.getFreeBoardById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateFreeBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        return freeBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletePostAndComments(@PathVariable(name = "id") Integer id) {
        return freeBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addPostToFreeBoard(@RequestBody BoardUpdateDTO freeBoard) {
        return freeBoardService.addPostToFreeBoard(freeBoard);
    }

    @PostMapping(value = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody CommentInputDTO comment) {
        return freeBoardCommentService.post(id, comment);
    }

    @GetMapping(value = "/{id}/comments")
    public List<CommentOutputDTO> getFreeBoardComment(@PathVariable(name = "id") Integer postId) {
        return freeBoardCommentService.getCommentByPostId(postId);
    }

    @GetMapping(value = "/{id}/comment/{commendId}")
    public List<CommentOutputDTO> getFreeBoardAllComments(@PathVariable(name = "id") Integer postId,
                                                            @PathVariable(name = "commendId") Integer commentId) {
        return freeBoardCommentService.getAllCommentByPostId(postId,commentId);
    }

    @PutMapping(value = "/{id}/comment/{commendId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FreeBoardCommentEntity updateFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commendId") Integer commentId,
            @RequestBody String content) {
        return freeBoardCommentService.updateComment(postId, commentId, content);
    }

    @DeleteMapping(value = "/{id}/comment/{commendId}")
    public ResponseEntity deleteFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commendId") Integer commentId) {
        return freeBoardCommentService.deleteComment(postId, commentId);
    }

}
