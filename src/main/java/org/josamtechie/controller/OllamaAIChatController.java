package org.josamtechie.controller;

import org.josamtechie.service.OllamaAIChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ollama/api")
public class OllamaAIChatController
{

    private final OllamaAIChatService service;

    public OllamaAIChatController(OllamaAIChatService service)
    {
        this.service = service;
    }

    @GetMapping("/chat")
    public String chatWithOllamaLlama(@RequestParam String message)
    {
        return service.chatWithOllamaLlama(message);
    }
}
