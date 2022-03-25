package com.servico.controller.api;

import com.servico.model.dto.PostDto;
import com.servico.service.CallAPIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final CallAPIService callAPIService;

    public PostController(CallAPIService callAPIService) {
        this.callAPIService = callAPIService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPostList() {
        List<PostDto> postDtoList = callAPIService.getPostList();
        if (postDtoList != null && !postDtoList.isEmpty())
            return ResponseEntity.ok(postDtoList);
        else
            return ResponseEntity.notFound().build();
    }
}
