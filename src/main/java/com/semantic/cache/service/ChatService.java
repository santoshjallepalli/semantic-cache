package com.semantic.cache.service;

import com.semantic.cache.dto.ChatRequest;
import com.semantic.cache.dto.ChatResponse;
import com.semantic.cache.entity.SemanticChatCache;
import com.semantic.cache.repository.SemanticCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SemanticCacheRepository repository;

    public ChatResponse chat(ChatRequest request){

        String aiResponse = "AI Response for : " + request.getPrompt();
        System.out.println("aiResponse = " + aiResponse);
        SemanticChatCache cache =
                SemanticChatCache.builder()
                        .prompt(request.getPrompt())
                        .response(aiResponse)
                        .build();
        repository.save(cache);
        return new ChatResponse(aiResponse);

    }

}
