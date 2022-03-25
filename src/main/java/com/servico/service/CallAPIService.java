package com.servico.service;

import com.servico.model.dto.PostDto;

import java.util.List;

public interface CallAPIService {
    List<PostDto> getPostList();
}
