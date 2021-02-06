package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.AccountRepository;
import com.springboot.intelllij.repository.FreeBoardCommentRepository;
import com.springboot.intelllij.repository.FreeBoardRepository;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.*;

@Service
public class FreeBoardCommentService {

    @Autowired
    FreeBoardCommentRepository freeBoardCommentRepo;

    @Autowired
    FreeBoardRepository freeBoardRepo;

    @Autowired
    AccountRepository accountRepo;

    private final int COMMENT_MAX = 3;
    private final int COMMENT_DEPTH = 0;
    private final int CHILD_COMMENT_DEPTH = 1;

    public ResponseEntity post(Integer postId, CommentInputDTO comment) {
        FreeBoardCommentEntity commentEntity = new FreeBoardCommentEntity();
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        FreeBoardEntity article = freeBoardRepo.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        commentEntity.setContent(comment.getContent());
        commentEntity.setPostId(postId);
        commentEntity.setCreatedAt(ZonedDateTime.now());
        commentEntity.setAccountId(accountId);

        if(comment.getBundleId() != null) {
            commentEntity.setBundleId(comment.getBundleId());
            commentEntity.setDepth(CHILD_COMMENT_DEPTH);
            freeBoardCommentRepo.save(commentEntity);

            FreeBoardCommentEntity parentEntity = freeBoardCommentRepo.findById(comment.getBundleId())
                    .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
            parentEntity.increaseBundleSize();
            freeBoardCommentRepo.save(parentEntity);
        } else {
            FreeBoardCommentEntity savedEntity = freeBoardCommentRepo.save(commentEntity);
            savedEntity.setBundleId(savedEntity.getId());
            freeBoardCommentRepo.save(savedEntity);
        }

        article.increaseReplyCnt();
        freeBoardRepo.save(article);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public List<CommentOutputDTO> getCommentByPostId(Integer postId) {
        AccountEntity user = UserUtils.getUserEntity();

        List<FreeBoardCommentEntity> comments = freeBoardCommentRepo.findByPostIdAndDepth(postId, COMMENT_DEPTH);
        Comparator<FreeBoardCommentEntity> comparator = Comparator.comparing(FreeBoardCommentEntity::getCreatedAt);
        comparator = comparator.thenComparingInt(FreeBoardCommentEntity::getBundleId);
        comments.sort(comparator);

        List<CommentOutputDTO> resultCommentList = new ArrayList<>();
        for(FreeBoardCommentEntity commentEntity : comments) {
            AccountEntity author = accountRepo.findById(commentEntity.getAccountId())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
            resultCommentList.add(new CommentOutputDTO(commentEntity, author.getNickname()));

            List<FreeBoardCommentEntity> childCommentList = freeBoardCommentRepo
                    .findByBundleIdAndDepthOrderByCreatedAtAsc(commentEntity.getId(), CHILD_COMMENT_DEPTH);

            if(childCommentList.isEmpty()) continue;

            for(int i = 0; i < childCommentList.size(); i++) {
                AccountEntity childCommentAuthor = accountRepo.findById(childCommentList.get(i).getAccountId())
                        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
                resultCommentList.add(new CommentOutputDTO(childCommentList.get(i), childCommentAuthor.getNickname()));
            }
        }

        resultCommentList = EntityUtils.setIsLiked(resultCommentList, user.getId(), LikeableTables.FREE_BOARD_COMMENT);
        return (List<CommentOutputDTO>) EntityUtils.setIsAuthor(resultCommentList, user.getId());
    }

    public List<CommentOutputDTO> getAllCommentByPostId(Integer postId, Integer commentId) {
        AccountEntity user = UserUtils.getUserEntity();
        FreeBoardCommentEntity originalComment = freeBoardCommentRepo.findByPostIdAndDepth(postId,COMMENT_DEPTH)
                .stream().filter(freeBoardCommentEntity -> freeBoardCommentEntity.getId().equals(commentId)).findFirst()
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
        List<FreeBoardCommentEntity> childCommentList = freeBoardCommentRepo.findByBundleIdAndDepthOrderByCreatedAtAsc(
                originalComment.getId(), CHILD_COMMENT_DEPTH
        );
        AccountEntity author = accountRepo.findById(originalComment.getAccountId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        List<CommentOutputDTO> resultCommentList = new ArrayList<>();
        resultCommentList.add(new CommentOutputDTO(originalComment, author.getNickname()));
        for(FreeBoardCommentEntity bundle: childCommentList) {

            AccountEntity bundleAuthor = accountRepo.findById(bundle.getAccountId())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
            resultCommentList.add(new CommentOutputDTO(bundle, bundleAuthor.getNickname()));
        }
        resultCommentList = EntityUtils.setIsLiked(resultCommentList, user.getId(), LikeableTables.FREE_BOARD_COMMENT);
        return (List<CommentOutputDTO>) EntityUtils.setIsAuthor(resultCommentList, user.getId());
    }

    public FreeBoardCommentEntity updateComment(Integer postId, Integer commentId, CommentInputDTO comment) {
        FreeBoardCommentEntity originalComment = freeBoardCommentRepo.findById(commentId).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
        originalComment.setContent(comment.getContent());
        return freeBoardCommentRepo.save(originalComment);
    }

    public ResponseEntity deleteComment(Integer postId, Integer commentId) {
        FreeBoardCommentEntity comment = freeBoardCommentRepo.findById(commentId).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
        FreeBoardEntity article = freeBoardRepo.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        if(comment.getDepth() == CHILD_COMMENT_DEPTH) {
            FreeBoardCommentEntity parentComment = freeBoardCommentRepo.findById(comment.getBundleId()).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
            parentComment.decreaseBundleSize();
            freeBoardCommentRepo.save(parentComment);
        }

        article.decreaseReplyCnt();
        freeBoardRepo.save(article);

        freeBoardCommentRepo.deleteById(commentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
