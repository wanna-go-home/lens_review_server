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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user = principal.toString();
        List<FreeBoardViewEntity> result = freeBoardPreviewRepo.findByEmail(user);

        result.sort(new BoardComparator());

        return result;
    }
}
