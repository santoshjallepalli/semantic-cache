package com.semantic.cache.controller;

import com.semantic.cache.dto.ChatRequest;
import com.semantic.cache.dto.ChatResponse;
import com.semantic.cache.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request){
        System.out.println("request.getPrompt() = " + request);
        return chatService.chat(request);

    }

}
