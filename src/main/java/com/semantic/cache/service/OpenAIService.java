package com.semantic.cache.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final ChatClient chatClient;

    public String getAIResponse(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
