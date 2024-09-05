import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {

        ImageHandler imageHandler = new ImageHandler();

        String inputImagePath = "C:\\Users\\User\\Pictures\\Saved Pictures\\photo_2023-11-30_20-35-36.jpg";

        String outputImagePath = "C:\\Users\\User\\Pictures\\Saved Pictures\\photo_2023-11-30_20-29-23.jpg";

        String outputImagePathMulti = "C:\\Users\\User\\Pictures\\Saved Pictures\\photo_2024-06-18_13-39-16.jpg";
        imageHandler.loadImage(inputImagePath);


        if (imageHandler.image == null) {
            System.out.println("Не вдалося завантажити зображення.");
            return;
        } else {
            System.out.println("Зображення успішно завантажено.");
        }
        // Вимірювання часу для однопоточного методу
        long startImageSingle = System.nanoTime();
        imageHandler.increaseBrightness(50);
        long endImageSingle = System.nanoTime();
        imageHandler.saveImage(outputImagePath);
        System.out.println("Однопоточне виконання: " + (endImageSingle - startImageSingle) / 1_000_000 + " мс");


        // Завантажуємо зображення знову, щоб обробити його багатопоточно

        imageHandler.loadImage(inputImagePath);

        // Вимірювання часу для багатопоточного методу
        long startTimeMulti = System.nanoTime();
        imageHandler.inncreaseBrightnessMultiThreaded(50);
        long endTimeMulti = System.nanoTime();
        imageHandler.saveImage(outputImagePathMulti);
        System.out.println("Багатопоточне виконання: " + (endTimeMulti - startTimeMulti) / 1_000_000 + " мс");

        imageHandler.loadImage(inputImagePath);

        imageHandler.increaseBrightnessMultiThreadHeight(50);
        imageHandler.saveImage(outputImagePath);


        int[] redHistogram = imageHandler.calculateHistogramMultiThread(0);

        for (int i = 0; i < redHistogram.length; i++) {
            System.out.println("Інтенсивність " + i + ": " + redHistogram[i]);
        }
        HistogramVisualizer visualizer = new HistogramVisualizer();

        int[] histogram = new int[256];

        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = (int) Math.random() * 200;
        }

        BufferedImage histogramImage = visualizer.generateHistogramImage(histogram);

        try {
            javax.imageio.ImageIO.write(histogramImage, "png", new java.io.File("histogram.png"));

        } catch (java.io.IOException e) {
            System.out.println("Помилка збереження зображення: " + e.getMessage());

        }
    }
}
