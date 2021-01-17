package com.springboot.intelllij.services;

import com.springboot.intelllij.constant.LikeableTables;
import com.springboot.intelllij.domain.FreeBoardViewEntity;
import com.springboot.intelllij.repository.FreeBoardPreviewRepository;
import com.springboot.intelllij.repository.LikeHistoryRepository;
import com.springboot.intelllij.utils.BoardComparator;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeBoardPreviewService {

    @Autowired
    FreeBoardPreviewRepository freeBoardPreviewRepo;

    @Autowired
    EntityUtils entityUtils;

    public List<FreeBoardViewEntity> getAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<FreeBoardViewEntity> result = freeBoardPreviewRepo.findAll();
        result.sort(new BoardComparator());

        result = entityUtils.setIsLiked(result, accountId, LikeableTables.FREE_BOARD);

        return (List<FreeBoardViewEntity>) EntityUtils.setIsAuthor(result, accountId);
    }

    public List<FreeBoardViewEntity> getMyAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<FreeBoardViewEntity> result = freeBoardPreviewRepo.findByAccountId(accountId);
        result.sort(new BoardComparator());
        result = entityUtils.setIsLiked(result, accountId, LikeableTables.FREE_BOARD);
        return (List<FreeBoardViewEntity>)EntityUtils.setIsAuthor(result, UserUtils.getUserIdFromSecurityContextHolder());
    }
}
