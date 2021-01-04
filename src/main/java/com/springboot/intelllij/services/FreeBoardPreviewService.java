package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardViewEntity;
import com.springboot.intelllij.repository.FreeBoardPreviewRepository;
import com.springboot.intelllij.utils.BoardComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeBoardPreviewService {

    @Autowired
    FreeBoardPreviewRepository freeBoardPreviewRepo;

    public List<FreeBoardViewEntity> getAllPreview() {
        List<FreeBoardViewEntity> result = freeBoardPreviewRepo.findAll();
        result.sort(new BoardComparator());
        return result;
    }

    public List<FreeBoardViewEntity> getMyAllPreview() {
        Integer accountId = (Integer)SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<FreeBoardViewEntity> result = freeBoardPreviewRepo.findByAccountId(accountId);

        result.sort(new BoardComparator());

        return result;
    }
}
