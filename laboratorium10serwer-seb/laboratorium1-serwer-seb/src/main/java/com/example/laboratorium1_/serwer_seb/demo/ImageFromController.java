package com.example.laboratorium1_.serwer_seb.demo;


import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/api/image")
public class ImageFromController {
    private BufferedImage uploadImage;

    // Метод для відображення форми завантаження
    @GetMapping
    public String showIndexPage() {
        return "index";
    }

    // Метод для обробки завантаження зображення
    @PostMapping("/upload")
    public String handleIMageUpload(@RequestParam("image") MultipartFile file, Model model, @RequestParam("brightness") int brightness) {
        try {

            // Перевірка, чи файл не пустий
            if (file.isEmpty()) {
                model.addAttribute("message", "Please select an image file");
                return "index";
            }


            // Конвертація завантаженого файлу в BufferedImage
            uploadImage = ImageIO.read(file.getInputStream());

            // Перевірка, чи зображення успішно прочитано
            if (uploadImage == null) {
                model.addAttribute("message", "Error reading image file");
                return "index";
            }

            // Зміна яскравості зображення
            BufferedImage brightnedImage = changeBrightness(uploadImage, brightness);


             // Передача URL для відображення зображення
            model.addAttribute("image", encodeImageToBase64(uploadImage));

            return "image";
        }catch (IOException e){
            e.printStackTrace();
            model.addAttribute("message", "Error processing image");
            return "index";
        }

    }

    // Метод для зміни яскравості зображення
    private BufferedImage changeBrightness(BufferedImage originalImage, int brightness) {
        BufferedImage brightenedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                Color color = new Color(originalImage.getRGB(x, y));
                int r = clamp(color.getRed() + brightness, 0, 255);
                int g = clamp(color.getGreen() + brightness, 0, 255);
                int b = clamp(color.getBlue() + brightness, 0, 255);
                Color newColor = new Color(r, g, b);
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


    // Метод для кодування зображення у формат base64!!!!!!!!!!!!!!!!!
    private String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
    // Метод для відображення зображення у HTML!!!!!!!!!!!!!!!!!!!!
    @GetMapping("image")
    public String getImage(Model model) throws IOException {
        if (uploadImage == null){
            model.addAttribute("message", "No image uploaded");
            return "index";
        }
        model.addAttribute("image", encodeImageToBase64(uploadImage));
        return "image";
    }

}


