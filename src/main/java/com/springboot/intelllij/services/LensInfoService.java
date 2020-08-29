package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.LensEntity;
import com.springboot.intelllij.repository.LensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LensInfoService {

    @Autowired
    LensRepository lensRepo;

    public List<LensEntity> getAllLensInfo() {
        return lensRepo.findAll();
    }

    public LensEntity getLensInfoById(Integer id) {
        return lensRepo.findById(id).orElseGet(() -> new LensEntity());
    }
}
