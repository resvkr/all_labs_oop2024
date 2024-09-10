package com.ebabak.artserver.controller;

import com.ebabak.artserver.entity.PixelRequest;
import com.ebabak.artserver.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {
    public final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping(value = "image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return imageService.getImageBytes();
    }

    @PostMapping(value = "pixel")
    public HttpStatus setPixel(@RequestBody PixelRequest request) {
        return imageService.setPixelForUser(request);
    }
}
