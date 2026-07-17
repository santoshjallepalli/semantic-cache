package com.semantic.cache.service;

import com.semantic.cache.dto.ChatRequest;
import com.semantic.cache.dto.ChatResponse;
import com.semantic.cache.entity.SemanticChatCache;
import com.semantic.cache.repository.SemanticCacheRepository;
import com.semantic.cache.repository.SemanticSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SemanticCacheRepository repository;

    private final EmbeddingService embeddingService;

    private final OpenAIService openAIService;

    private final SemanticSearchRepository semanticSearchRepository;

    public ChatResponse chat(ChatRequest request){

        String prompt = request.getPrompt();
        double threshold = (request.getThreshold() == null) ? 0.90d : request.getThreshold();
        System.out.println("prompt = " + prompt);

        float[] embedding = embeddingService.generateEmbedding(prompt);
        System.out.println("embedding = " + embedding);
        
        Optional<SemanticChatCache> semanticCache =
                semanticSearchRepository.findBestMatch(
                        embedding,
                        threshold);
        System.out.println("semanticCache = " + semanticCache);
        if (semanticCache.isPresent()) {
            return ChatResponse.builder()
                    .response(semanticCache.get().getResponse())
                    .cacheHit(true)
                    .build();
        }
        String aiResponse = openAIService.getAIResponse(prompt);
        System.out.println("aiResponse = " + aiResponse);

        SemanticChatCache entity =
                SemanticChatCache.builder()
                        .prompt(prompt)
                        .response(aiResponse)
                        .embedding(embedding)
                        .build();

        repository.save(entity);

        return ChatResponse.builder()
                .response(aiResponse)
                .cacheHit(false)
                .build();
    }
}
