package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.AccountRepository;
import com.springboot.intelllij.repository.ReviewBoardCommentRepository;
import com.springboot.intelllij.repository.ReviewBoardRepository;
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

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.COMMENT_NOT_FOUND;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.POST_NOT_FOUND;
import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.USER_NOT_FOUND;

@Service
public class ReviewBoardCommentService {

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepo;

    @Autowired
    ReviewBoardRepository reviewBoardRepo;

    @Autowired
    AccountRepository accountRepo;

    private final int COMMENT_MAX = 3;
    private final int COMMENT_DEPTH = 0;
    private final int CHILD_COMMENT_DEPTH = 1;

    public void post(Integer postId, CommentInputDTO comment) {
        ReviewBoardCommentEntity commentEntity = new ReviewBoardCommentEntity();
        Integer accountId = UserUtils.getUserIdFromSecurityContextHolder();
        ReviewBoardEntity review = reviewBoardRepo.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        commentEntity.setContent(comment.getContent());
        commentEntity.setPostId(postId);
        commentEntity.setCreatedAt(ZonedDateTime.now());
        commentEntity.setAccountId(accountId);

        if(comment.getBundleId() != null) {
            commentEntity.setBundleId(comment.getBundleId());
            commentEntity.setDepth(CHILD_COMMENT_DEPTH);
            reviewBoardCommentRepo.save(commentEntity);

            ReviewBoardCommentEntity parentEntity = reviewBoardCommentRepo.findById(comment.getBundleId())
                    .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
            parentEntity.increaseBundleSize();
            reviewBoardCommentRepo.save(parentEntity);
        } else {
            ReviewBoardCommentEntity savedEntity = reviewBoardCommentRepo.save(commentEntity);
            savedEntity.setBundleId(savedEntity.getId());
            reviewBoardCommentRepo.save(savedEntity);
        }

        review.increaseReplyCnt();
        reviewBoardRepo.save(review);
    }

    public List<CommentOutputDTO> getCommentByPostId(Integer postId) {
        AccountEntity user = UserUtils.getUserEntity();
        List<ReviewBoardCommentEntity> comments = reviewBoardCommentRepo.findByPostIdAndDepth(postId,COMMENT_DEPTH);
        List<CommentOutputDTO> resultCommentList = new ArrayList<>();

        Comparator<ReviewBoardCommentEntity> comparator = Comparator.comparing(ReviewBoardCommentEntity::getCreatedAt);
        comparator = comparator.thenComparingInt(ReviewBoardCommentEntity::getBundleId);
        comments.sort(comparator);

        for(ReviewBoardCommentEntity commentEntity : comments) {
            List<ReviewBoardCommentEntity> childCommentList = reviewBoardCommentRepo.findByBundleIdAndDepthOrderByCreatedAtAsc(commentEntity.getId(),CHILD_COMMENT_DEPTH);
            AccountEntity author = accountRepo.findById(commentEntity.getAccountId())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
            resultCommentList.add(new CommentOutputDTO(commentEntity, author.getNickname()));

            if(childCommentList.isEmpty()) continue;

            for(int i = 0; i < childCommentList.size(); i++) {
                if(i >= COMMENT_MAX) break;
                AccountEntity childCommentAuthor = accountRepo.findById(childCommentList.get(i).getAccountId())
                        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
                resultCommentList.add(new CommentOutputDTO(childCommentList.get(i), childCommentAuthor.getNickname()));
            }
        }

        resultCommentList = EntityUtils.setIsLiked(resultCommentList, user.getId(), LikeableTables.REVIEW_BOARD_COMMENT);
        return (List<CommentOutputDTO>)EntityUtils.setIsAuthor(resultCommentList, user.getId());
    }

    public List<CommentOutputDTO> getAllCommentByPostId(Integer postId, Integer commentId) {
        AccountEntity user = UserUtils.getUserEntity();
        ReviewBoardCommentEntity originalComment = reviewBoardCommentRepo.findByPostIdAndDepth(postId,COMMENT_DEPTH)
                .stream().filter(reviewBoardCommentEntity -> reviewBoardCommentEntity.getId().equals(commentId)).findFirst()
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
        List<ReviewBoardCommentEntity> commentsOfComment = reviewBoardCommentRepo.findByBundleIdAndDepthOrderByCreatedAtAsc(originalComment.getId(),CHILD_COMMENT_DEPTH);
        List<CommentOutputDTO> resultCommentList = new ArrayList<>();
        AccountEntity author = accountRepo.findById(originalComment.getAccountId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        resultCommentList.add(new CommentOutputDTO(originalComment, author.getNickname()));
        for(ReviewBoardCommentEntity bundle: commentsOfComment) {
            AccountEntity bundleAuthor = accountRepo.findById(bundle.getAccountId())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
            resultCommentList.add(new CommentOutputDTO(bundle, bundleAuthor.getNickname()));
        }

        resultCommentList = EntityUtils.setIsLiked(resultCommentList, user.getId(), LikeableTables.REVIEW_BOARD_COMMENT);
        return (List<CommentOutputDTO>) EntityUtils.setIsAuthor(resultCommentList, user.getId());
    }

    public ReviewBoardCommentEntity updateComment(Integer postId, Integer commentId, CommentInputDTO comment) {
        ReviewBoardCommentEntity originalComment = reviewBoardCommentRepo.findById(commentId).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));

        originalComment.setContent(comment.getContent());

        return reviewBoardCommentRepo.save(originalComment);
    }

    public void deleteComment(Integer postId, Integer commentId) {
        ReviewBoardCommentEntity comment = reviewBoardCommentRepo.findById(commentId).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
        ReviewBoardEntity review = reviewBoardRepo.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        if(comment.getDepth() == CHILD_COMMENT_DEPTH) {
            ReviewBoardCommentEntity parentComment = reviewBoardCommentRepo.findById(comment.getBundleId()).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
            parentComment.decreaseBundleSize();
            reviewBoardCommentRepo.save(parentComment);
        }

        review.decreaseReplyCnt();
        reviewBoardRepo.save(review);

        reviewBoardCommentRepo.deleteById(commentId);
    }
}
