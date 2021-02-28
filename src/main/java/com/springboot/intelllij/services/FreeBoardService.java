package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.BoardUpdateDTO;
import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.FreeBoardCommentRepository;
import com.springboot.intelllij.repository.FreeBoardRepository;
import com.springboot.intelllij.utils.BoardComparator;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.BOARD_NOT_FOUND;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.POST_NOT_FOUND;

@Service
public class FreeBoardService {

    @Autowired
    FreeBoardRepository freeBoardRepo;
    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepository;

    public List<FreeBoardEntity> getAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<FreeBoardEntity> result = freeBoardRepo.findAll();
        result.sort(new BoardComparator());

        result = EntityUtils.setIsLiked(result, accountId, LikeableTables.FREE_BOARD);

        return (List<FreeBoardEntity>) EntityUtils.setIsAuthor(result, accountId);
    }

    public List<FreeBoardEntity> getMyAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<FreeBoardEntity> result = freeBoardRepo.findByAccountId(accountId);
        result.sort(new BoardComparator());
        result = EntityUtils.setIsLiked(result, accountId, LikeableTables.FREE_BOARD);
        return (List<FreeBoardEntity>)EntityUtils.setIsAuthor(result, UserUtils.getUserIdFromSecurityContextHolder());
    }

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
    public FreeBoardEntity getFreeBoardById(Integer id) {
        FreeBoardEntity freeBoardEntity = freeBoardRepo.findById(id).
                orElseThrow(() -> new NotFoundException(BOARD_NOT_FOUND));
        freeBoardEntity.setViewCnt(freeBoardEntity.getViewCnt() + 1);
        freeBoardEntity = freeBoardRepo.save(freeBoardEntity);
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        freeBoardEntity = EntityUtils.setIsLiked(freeBoardEntity, accountId, LikeableTables.FREE_BOARD, id);
        return EntityUtils.setIsAuthor(freeBoardEntity, accountId);
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
