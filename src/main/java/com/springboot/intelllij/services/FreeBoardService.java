package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.FreeBoardViewEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.FreeBoardCommentRepository;
import com.springboot.intelllij.repository.FreeBoardPreviewRepository;
import com.springboot.intelllij.repository.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.POST_NOT_FOUND;

@Service
public class FreeBoardService {

    @Autowired
    FreeBoardRepository freeBoardRepo;
    @Autowired
    FreeBoardPreviewRepository freeBoardPreviewRepository;
    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepository;

    public List<FreeBoardEntity> getAllPosts() { return freeBoardRepo.findAll(); }

    public ResponseEntity addPostToFreeBoard(FreeBoardEntity freeBoard) {
        freeBoardRepo.save(freeBoard);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public Optional<FreeBoardViewEntity> getFreeBoardById(Integer id) { return freeBoardPreviewRepository.findById(id); }

    @Transactional
    public ResponseEntity deletePostAndComments(Integer id) {
        freeBoardCommentRepository.deleteByPostId(id);
        FreeBoardEntity freeBoardEntity = freeBoardRepo.findById(id).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
        freeBoardRepo.delete(freeBoardEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updatePost(Integer id, String title, String content) {
        freeBoardRepo.updateFreeBoardEntity(id, title, content);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
