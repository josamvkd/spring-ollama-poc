package org.josamtechie.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OllamaAIChatService
{

    private final ChatClient chatClient;

    public OllamaAIChatService(ChatClient.Builder chatClientBuilder)
    {
        this.chatClient = chatClientBuilder.build();
    }

    public String chatWithOllamaLlama(String message)
    {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
