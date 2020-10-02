package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.LensEntity;
import com.springboot.intelllij.domain.LensPreviewEntity;
import com.springboot.intelllij.services.LensInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RESTPath.LENS_INFO)
public class LensInfoController {

    @Autowired
    LensInfoService lensInfoService;

    @GetMapping
    public List<LensEntity> getLensInfo(){
        return lensInfoService.getAllLensInfo();
    }

    @GetMapping(params = {"id"})
    public LensEntity getLensInfoById(@RequestParam("id") Integer id) {
        return lensInfoService.getLensInfoById(id);
    }

    @GetMapping(value = RESTPath.LENS_PREVIEW)
    public List<LensPreviewEntity> getLensesPreview() { return lensInfoService.getLensesPreview(); }
}
