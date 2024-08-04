package org.springframework.ai.openai.samples.helloworld;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.samples.helloworld.Service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AIController {
	private final ChatClient chatClient;

	@Autowired
	private ImageGenerationService imageGenerationService;

	AIController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@GetMapping("/ai")
	Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return Map.of(
				"completion",
				chatClient.prompt()
						.user(message)
						.call()
						.content());
	}

	@GetMapping("/ImageGeneration")
	Map<String, String> generateImage(@RequestParam(value = "prompt", defaultValue = "temple of china") String prompt) { 
		return Map.of("imageUrl", imageGenerationService.generateImage(prompt));
	}
}
