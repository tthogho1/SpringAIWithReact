package org.springframework.ai.openai.samples.helloworld.Service;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class ImageGenerationService {

    @Resource
    private OpenAiImageModel imageModel;

    public String generateImage(String prompt) {
        
        ImageResponse response = imageModel.call(
        
            new ImagePrompt(prompt,
                OpenAiImageOptions.builder()
                        .withModel("dall-e-2")
                        .withN(1)
                        .withHeight(256)
                        .withWidth(256)
                        .withResponseFormat("url").build())
        );
        
        return response.getResult().getOutput().getUrl();
    }

}
