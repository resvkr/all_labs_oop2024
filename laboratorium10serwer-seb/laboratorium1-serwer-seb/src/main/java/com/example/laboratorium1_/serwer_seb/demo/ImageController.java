package com.example.laboratorium1_.serwer_seb.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    // Метод для обробки GET-запитів, який приймає зображення у форматі base64 і значення яскравості
    @GetMapping("/adjust-brightness")
    public String adjustBrightness(@RequestParam String imageBase64, @RequestParam int brightness) {
        try {
            // Декодування base64-рядка у байти зображення
            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bais);

            // Перевірка, чи зображення успішно прочитано
            if (image == null) {
                throw new IllegalArgumentException("Error reading PNG image data");
            }

            // Зміна яскравості зображення
            BufferedImage brightenedImage = changeBrightness(image, brightness);

            // Конвертація зображення назад у base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(brightenedImage, "png", baos);
            byte[] brightenedImageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(brightenedImageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing image: " + e.getMessage();
        }
    }
    // Новий метод для обробки GET-запитів, який повертає зображення у форматі байтів
    @GetMapping("/get-image")
    public void getImage(@RequestParam String imageBase64, @RequestParam int brightness, OutputStream outputStream){
        try {
            // Декодування base64-рядка у байти зображення
            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bais);

            // Перевірка, чи зображення успішно прочитано
            if (image == null) {
                throw new IllegalArgumentException("Error reading PNG image data");
            }

            // Зміна яскравості зображення
            BufferedImage brightenedImage = changeBrightness(image, brightness);

            // Запис зображення у форматі PNG до OutputStream
            ImageIO.write(brightenedImage, "png", outputStream);
        }catch (IOException e) {
            e.printStackTrace();
            // Виведення помилки у відповідь
            throw new RuntimeException("Error processing image: " + e.getMessage());
        }
    }



    // Метод для зміни яскравості зображення
    private BufferedImage changeBrightness(BufferedImage originalImage, int brightness){
        // Створення нового зображення з тими ж розмірами і типом
        BufferedImage brightenedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        // Обхід кожного пікселя зображення
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                // Отримання кольору поточного пікселя
                Color color = new Color(originalImage.getRGB(x, y));

                // Зміна значень для червоного, зеленого і синього каналів
                int r = clamp(color.getRed() + brightness, 0, 255);
                int g = clamp(color.getGreen() + brightness, 0, 255);
                int b = clamp(color.getBlue() + brightness, 0, 255);

                // Створення нового кольору з новими значеннями каналів
                Color newColor = new Color(r, g, b);

                // Встановлення нового кольору для пікселя
                brightenedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return brightenedImage;
    }

    // Метод для обмеження значення в діапазоні [0, 255]
    private int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
