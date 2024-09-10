package com.ebabak.artserver.config;

import com.ebabak.artserver.db.PixelRequestJPA;
import com.ebabak.artserver.entity.PixelRequest;
import com.ebabak.artserver.service.ImageService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {
    private final ImageService imageService;
    private final PixelRequestJPA pixelRequestJPA;

    public AppConfig(ImageService imageService, PixelRequestJPA pixelRequestJPA) {
        this.imageService = imageService;
        this.pixelRequestJPA = pixelRequestJPA;
    }

    @PostConstruct
    public void fillImage() {
        List<PixelRequest> pixelRequests = pixelRequestJPA.findAll();
        for (PixelRequest pixelRequest : pixelRequests) {
            imageService.setPixel(pixelRequest);
        }
    }
}
