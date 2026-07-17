package com.semantic.cache.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public float[] generateEmbedding(String text) {

        EmbeddingResponse response = embeddingModel.call(
                new EmbeddingRequest(
                        List.of(text),
                        OpenAiEmbeddingOptions.builder()
                                .withModel("text-embedding-3-large")
                                .build()
                ));
        System.out.println("response = " + response);
        return response.getResults().getFirst().getOutput();
    }

}
