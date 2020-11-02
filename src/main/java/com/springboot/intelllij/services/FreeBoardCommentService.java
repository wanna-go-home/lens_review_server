package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import com.springboot.intelllij.repository.FreeBoardCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;

@Service
public class FreeBoardCommentService {

    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepo;

    public ResponseEntity post(@RequestBody FreeBoardCommentEntity comment) {
        freeBoardCommentRepo.save(comment);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public List<FreeBoardCommentEntity> getCommentByPostId(Integer postId) {
        List<FreeBoardCommentEntity> comments = freeBoardCommentRepo.findByPostId(postId);

        Comparator<FreeBoardCommentEntity> comparator = Comparator.comparing(FreeBoardCommentEntity::getCreatedAt);
        comparator = comparator.thenComparingInt(FreeBoardCommentEntity::getBundleId);
        comments.sort(comparator);

        return comments;
    }
}
