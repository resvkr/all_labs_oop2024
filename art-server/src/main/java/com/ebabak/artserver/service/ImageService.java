package com.ebabak.artserver.service;

import com.ebabak.artserver.db.PixelRequestJPA;
import com.ebabak.artserver.entity.PixelRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HexFormat;

@Service
public class ImageService {
    private final int width = 512;
    private final int height = 512;

    private final UserService userService;
    private final PixelRequestJPA pixelRequestJPA;

    private final BufferedImage image;

    public ImageService(UserService userService, PixelRequestJPA pixelRequestJPA) {
        this.userService = userService;
        this.pixelRequestJPA = pixelRequestJPA;

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        setDefaultImage();
        g2d.dispose();
    }

    private void setDefaultImage() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, 0);
            }
        }
    }

    public byte[] getImageBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // make image support format png
        ImageIO.write(image, "png", baos);

        return baos.toByteArray();
    }

    public HttpStatus setPixelForUser(PixelRequest request) {
        if (!userService.isUserActive(request.getToken())) {
            return HttpStatus.FORBIDDEN;
        }

        HttpStatus httpStatus = setPixel(request);
        if (httpStatus == HttpStatus.BAD_REQUEST) {
            return httpStatus;
        }

        pixelRequestJPA.save(request);
        userService.restrictDrawingForUser(request.getToken());
        return httpStatus;
    }

    public HttpStatus setPixel(PixelRequest request) {
        try {
            int rgb = HexFormat.fromHexDigits(request.getColor());
            image.setRGB(request.getX(), request.getY(), rgb);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
