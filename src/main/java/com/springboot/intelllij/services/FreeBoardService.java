package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.BoardUpdateDTO;
import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.FreeBoardViewEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.FreeBoardCommentRepository;
import com.springboot.intelllij.repository.FreeBoardPreviewRepository;
import com.springboot.intelllij.repository.FreeBoardRepository;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.BOARD_NOT_FOUND;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.POST_NOT_FOUND;

@Service
public class FreeBoardService {

    @Autowired
    FreeBoardRepository freeBoardRepo;
    @Autowired
    FreeBoardPreviewRepository freeBoardPreviewRepository;
    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepository;
    @Autowired
    EntityUtils entityUtils;

    public ResponseEntity addPostToFreeBoard(BoardUpdateDTO freeBoard) {
        Integer accountId = (Integer)SecurityContextHolder.getContext().getAuthentication().getDetails();

        FreeBoardEntity entity = new FreeBoardEntity();
        entity.setAccountId(accountId);
        entity.setContent(freeBoard.getContent());
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setTitle(freeBoard.getTitle());

        freeBoardRepo.save(entity);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Transactional
    public FreeBoardViewEntity getFreeBoardById(Integer id) {
        FreeBoardViewEntity freeBoardViewEntity = freeBoardPreviewRepository.findById(id).
                orElseThrow(() -> new NotFoundException(BOARD_NOT_FOUND));
        freeBoardViewEntity.setViewCnt(freeBoardViewEntity.getViewCnt() + 1);
        freeBoardViewEntity = freeBoardPreviewRepository.save(freeBoardViewEntity);

        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        freeBoardViewEntity = entityUtils.setIsLiked(freeBoardViewEntity, accountId, LikeableTables.FREE_BOARD, id);
        return EntityUtils.setIsAuthor(freeBoardViewEntity, accountId);
    }

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
