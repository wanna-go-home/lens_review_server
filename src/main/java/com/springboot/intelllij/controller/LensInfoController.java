package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.LensEntity;
import com.springboot.intelllij.domain.LensPreviewEntity;
import com.springboot.intelllij.services.LensInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.LENS_INFO)
public class LensInfoController {

    @Autowired
    LensInfoService lensInfoService;

    @GetMapping
    public List<LensPreviewEntity> getLensesPreview(int page, int size) {
        return lensInfoService.getLensesPreview(PageRequest.of(page,size));
    }

    @GetMapping(value = RESTPath.ID)
    public LensEntity getLensInfoById(@PathVariable(name = "id") Integer id) {
        return lensInfoService.getLensInfoById(id);
    }

}
