package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.FreeBoardCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.COMMENT_NOT_FOUND;

@Service
public class FreeBoardCommentService {

    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepo;

    private final int COMMENT_MAX = 3;
    private final int COMMENT_DEPTH = 0;
    private final int COMMENT_OF_COMMENT_DEPTH = 1;

    public ResponseEntity post( FreeBoardCommentEntity comment) {
        freeBoardCommentRepo.save(comment);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public List<FreeBoardCommentEntity> getCommentByPostId(Integer postId) {
        List<FreeBoardCommentEntity> comments = freeBoardCommentRepo.findByPostIdAndDepth(postId,COMMENT_DEPTH);
        List<FreeBoardCommentEntity> resultCommentList = new ArrayList<>();

        Comparator<FreeBoardCommentEntity> comparator = Comparator.comparing(FreeBoardCommentEntity::getCreatedAt);
        comparator = comparator.thenComparingInt(FreeBoardCommentEntity::getBundleId);
        comments.sort(comparator);

        for(FreeBoardCommentEntity commentEntity : comments) {
            List<FreeBoardCommentEntity> commentsOfComment = freeBoardCommentRepo.findByBundleIdAndDepth(commentEntity.getId(),COMMENT_OF_COMMENT_DEPTH);
            resultCommentList.add(commentEntity);

            if(commentsOfComment.isEmpty()) continue;

            for(int i = 0; i < commentsOfComment.size(); i++) {
                if(i > COMMENT_MAX) break;
                resultCommentList.add(commentsOfComment.get(i));
            }
        }

        return resultCommentList;
    }

    public List<FreeBoardCommentEntity> getAllCommentByPostId(Integer postId, Integer commentId) {
        FreeBoardCommentEntity originalComment = freeBoardCommentRepo.findByPostIdAndDepth(postId,COMMENT_DEPTH)
                .stream().filter(freeBoardCommentEntity -> freeBoardCommentEntity.getId().equals(commentId)).findFirst()
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
        List<FreeBoardCommentEntity> commentsOfComment = freeBoardCommentRepo.findByBundleIdAndDepth(originalComment.getId(),COMMENT_OF_COMMENT_DEPTH);
        List<FreeBoardCommentEntity> resultCommentList = new ArrayList<>();

        resultCommentList.add(originalComment);

        commentsOfComment.forEach(bundle -> {
            resultCommentList.add(bundle);
        });

        return resultCommentList;
    }
}
