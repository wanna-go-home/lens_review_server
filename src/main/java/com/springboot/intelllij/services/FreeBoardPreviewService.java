package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardPreviewEntity;
import com.springboot.intelllij.repository.FreeBoardPreviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeBoardPreviewService {

    @Autowired
    FreeBoardPreviewRepository freeBoardPreviewRepo;

    public List<FreeBoardPreviewEntity> getAllPreview() { return freeBoardPreviewRepo.findAll(); }
}
