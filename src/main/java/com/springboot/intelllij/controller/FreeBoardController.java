package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.FreeBoardCommentService;
import com.springboot.intelllij.services.FreeBoardPreviewService;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.springboot.intelllij.constant.RESTPath.*;

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

    @GetMapping(value = POST_ID)
    public FreeBoardViewEntity getFreeBoardById(@PathVariable(name = "id") Integer id) {
        return freeBoardService.getFreeBoardById(id);
    }

    @PutMapping(value = POST_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateFreeBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        return freeBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = POST_ID)
    public ResponseEntity deletePostAndComments(@PathVariable(name = "id") Integer id) {
        return freeBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addPostToFreeBoard(@RequestBody BoardUpdateDTO freeBoard) {
        return freeBoardService.addPostToFreeBoard(freeBoard);
    }

    @PostMapping(value = POST_LIKE)
    public ResponseEntity<FreeBoardEntity> postLiked(@PathVariable(name = "id") Integer id) {
        FreeBoardEntity freeBoardEntity = freeBoardService.postLiked(id);
        return new ResponseEntity<>(freeBoardEntity, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = POST_COMMENTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody CommentDTO comment) {
        return freeBoardCommentService.post(id, comment);
    }

    @GetMapping(value = POST_COMMENTS)
    public List<FreeBoardCommentEntity> getFreeBoardComment(@PathVariable(name = "id") Integer postId) {
        return freeBoardCommentService.getCommentByPostId(postId);
    }

    @GetMapping(value = POST_AND_COMMENT_ID)
    public List<FreeBoardCommentEntity> getFreeBoardAllComments(@PathVariable(name = "id") Integer postId,
                                                            @PathVariable(name = "commendId") Integer commentId) {
        return freeBoardCommentService.getAllCommentByPostId(postId, commentId);
    }

    @PutMapping(value = POST_AND_COMMENT_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FreeBoardCommentEntity updateFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commendId") Integer commentId,
            @RequestBody String content) {
        return freeBoardCommentService.updateComment(postId, commentId, content);
    }

    @DeleteMapping(value = POST_AND_COMMENT_ID)
    public ResponseEntity deleteFreeBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commendId") Integer commentId) {
        return freeBoardCommentService.deleteComment(postId, commentId);
    }

}
