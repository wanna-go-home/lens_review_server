package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.FreeBoardCommentService;
import com.springboot.intelllij.services.FreeBoardPreviewService;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public List<FreeBoardViewEntity>  getFreeBoardPreviews(int page, int size) {
        return freeBoardPreviewService.getAllPreview(PageRequest.of(page,size));
    }

    @GetMapping(value = RESTPath.ID)
    public FreeBoardViewEntity getFreeBoardById(@PathVariable(name = "id") Integer id) {
        return freeBoardService.getFreeBoardById(id);
    }

    @PutMapping(value = RESTPath.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateFreeBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        return freeBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = RESTPath.ID)
    public ResponseEntity deletePostAndComments(@PathVariable(name = "id") Integer id) {
        return freeBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addPostToFreeBoard(@RequestBody BoardUpdateDTO freeBoard) {
        return freeBoardService.addPostToFreeBoard(freeBoard);
    }

    @PostMapping(value = RESTPath.COMMENTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody CommentInputDTO comment) {
        return freeBoardCommentService.post(id, comment);
    }

    @GetMapping(value = RESTPath.COMMENTS)
    public List<CommentOutputDTO> getFreeBoardComment(@PathVariable(name = "id") Integer postId) {
        return freeBoardCommentService.getCommentByPostId(postId);
    }

    @GetMapping(value = RESTPath.COMMENT_ID)
    public List<CommentOutputDTO> getFreeBoardAllComments(
            @PathVariable(name = "id") Integer postId, @PathVariable(name = "commentId") Integer commentId) {
        return freeBoardCommentService.getAllCommentByPostId(postId,commentId);
    }

    @PutMapping(value = RESTPath.COMMENT_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FreeBoardCommentEntity updateFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId,
            @RequestBody CommentInputDTO comment) {
        return freeBoardCommentService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping(value = RESTPath.COMMENT_ID)
    public ResponseEntity deleteFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId) {
        return freeBoardCommentService.deleteComment(postId, commentId);
    }

}
