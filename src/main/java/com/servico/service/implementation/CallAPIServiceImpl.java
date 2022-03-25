package com.servico.service.implementation;

import com.servico.model.dto.PostDto;
import com.servico.service.CallAPIService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CallAPIServiceImpl implements CallAPIService {

    private final WebClient localApiClient;

    public CallAPIServiceImpl(@NotNull WebClient.Builder webClientBuilder) {
        this.localApiClient = webClientBuilder.build();
    }

    @Override
    public List<PostDto> getPostList() {
        try {
            Mono<List<PostDto>> response = localApiClient
                    .get()
                    .uri("https://jsonplaceholder.typicode.com/posts")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<PostDto>>() {
                    });

            return response.block();
        } catch (Exception ex) {
            return null;
        }
    }
}
