package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.BoardUpdateDTO;
import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.FreeBoardViewEntity;
import com.springboot.intelllij.services.FreeBoardCommentService;
import com.springboot.intelllij.services.FreeBoardPreviewService;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<FreeBoardViewEntity> getFreeBoardById(@PathVariable(name = "id") Integer id) {
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
    public ResponseEntity addPostToFreeBoard(@RequestBody FreeBoardEntity freeBoard) {
        return freeBoardService.addPostToFreeBoard(freeBoard);
    }

    @PostMapping(value = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody FreeBoardCommentEntity comment) {
        comment.setPostId(id);
        return freeBoardCommentService.post(comment);
    }

    @GetMapping(value = "/{id}/comments")
    public List<FreeBoardCommentEntity> getFreeBoardComment(@PathVariable(name = "id") Integer postId) {
        return freeBoardCommentService.getCommentByPostId(postId);
    }
}
