package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.LensEntity;
import com.springboot.intelllij.services.LensInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LensInfoController {

    @Autowired
    LensInfoService lensInfoService;

    @GetMapping("/api/lensinfo")
    public List<LensEntity> getLensInfo(){
        return lensInfoService.getAllLensInfo();
    }

    @GetMapping(value = "/api/lensinfo", params = {"id"})
    public LensEntity getLensInfoById(@RequestParam("id") Integer id) {
        return lensInfoService.getLensInfoById(id);
    }


}
