package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardViewEntity;
import com.springboot.intelllij.repository.FreeBoardPreviewRepository;
import com.springboot.intelllij.utils.BoardComparator;
import com.springboot.intelllij.utils.EntityUtils;
import com.springboot.intelllij.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeBoardPreviewService {

    @Autowired
    FreeBoardPreviewRepository freeBoardPreviewRepo;

    public List<FreeBoardViewEntity> getAllPreview() {
        List<FreeBoardViewEntity> result = freeBoardPreviewRepo.findAll();
        result.sort(new BoardComparator());

        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        return (List<FreeBoardViewEntity>) EntityUtils.setIsAuthor(result, accountId);
    }

    public List<FreeBoardViewEntity> getMyAllPreview() {
        int accountId = UserUtils.getUserIdFromSecurityContextHolder();
        List<FreeBoardViewEntity> result = freeBoardPreviewRepo.findByAccountId(accountId);
        result.sort(new BoardComparator());

        return (List<FreeBoardViewEntity>)EntityUtils.setIsAuthor(result, UserUtils.getUserIdFromSecurityContextHolder());
    }
}
