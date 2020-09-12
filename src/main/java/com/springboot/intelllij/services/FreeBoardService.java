package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.FreeBoardPreview;
import com.springboot.intelllij.repository.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class FreeBoardService {

    @Autowired
    FreeBoardRepository freeBoardRepo;

    public List<FreeBoardEntity> getAllPosts() { return freeBoardRepo.findAll(); }

    public ResponseEntity addPostToFreeBoard(@RequestBody FreeBoardEntity freeBoard) {
        freeBoardRepo.save(freeBoard);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public List<FreeBoardPreview> getAllPreviews() {
        //FIXME - To Const
        int previewRange = 100;
        List<FreeBoardPreview> previews = new ArrayList<>();
        List<FreeBoardEntity> allPosts = freeBoardRepo.findAll();
        for(FreeBoardEntity post : allPosts) {
            String content = post.getContent();
            String shortContent = content.substring(0, Math.min(content.length(), previewRange));
            previews.add(new FreeBoardPreview(post.getId(), post.getUserId(), post.getTitle(), shortContent));
        }
        return previews;
    }
}
