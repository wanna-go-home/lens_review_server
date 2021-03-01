package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.LensEntity;
import com.springboot.intelllij.domain.LensPreviewEntity;
import com.springboot.intelllij.services.LensInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.LENS_INFO)
public class LensInfoController {

    @Autowired
    LensInfoService lensInfoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LensPreviewEntity> getLensesPreview() { return lensInfoService.getLensesPreview(); }

    @GetMapping(value = RESTPath.ID)
    @ResponseStatus(HttpStatus.OK)
    public LensEntity getLensInfoById(@PathVariable(name = "id") Integer id) {
        return lensInfoService.getLensInfoById(id);
    }

}
