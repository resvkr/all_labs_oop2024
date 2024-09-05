import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ImageHandler {
    BufferedImage image;

    // Метод для зчитування зображення з заданого шляху
    public void loadImage(String filepath) {
        try {
            // Зчитуємо зображення з файлу
            image = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            System.out.println("Помилка зчитування зображення: " + e.getMessage());
        }
    }

    // Метод для збереження зображення до заданого шляху
    public void saveImage(String path) {
        try {
            // Отримуємо розширення файлу з шляху (наприклад, "jpg", "png")
            String extention = path.substring(path.lastIndexOf(".") + 1);
            // Записуємо зображення до файлу
            ImageIO.write(image, extention, new File(path));
        } catch (IOException e) {
            System.out.println("Помилка збереження зображення: " + e.getMessage());
        }
    }

    // Метод для збільшення яскравості зображення на задану величину
    public void increaseBrightness(int value) {

        // Перевіряємо, чи зображення було завантажено
        if (image == null) {
            System.out.println("Зображення не завантажено");
        }
        // Отримуємо ширину і висоту зображення
        int width = image.getWidth();
        int height = image.getHeight();
// Проходимо по кожному пікселю зображення
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Отримуємо колір пікселя
                Color color = new Color(image.getRGB(x, y));

                // Збільшуємо яскравість для кожного каналу (R, G, B)
                int red = clamp(color.getRed() + value);
                int green = clamp(color.getGreen() + value);
                int blue = clamp(color.getBlue() + value);
                // Створюємо новий колір з оновленими значеннями
                Color newColor = new Color(red, green, blue);
// Встановлюємо новий колір на піксель
                image.setRGB(x, y, newColor.getRGB());

            }
        }


    }

    // Допоміжний метод для обмеження значення між 0 і 255
    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }


    public void inncreaseBrightnessMultiThreaded(int value) {
        if (image == null) {
            System.err.println("Зображення не завантажено!");
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        // Отримуємо кількість доступних ядер процесора
        int cores = Runtime.getRuntime().availableProcessors();

        // Створюємо пул потоків з кількістю потоків, рівною кількості ядер
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Ділимо зображення на вертикальні сегменти для обробки у різних потоках
        int chunkSize = width / cores;


        for (int i = 0; i < cores; i++) {
            int startX = i * chunkSize;
            int endX;
            if (i == cores - 1) {
                // Если это последний поток, то его сегмент должен включать все оставшиеся пиксели до конца изображения
                endX = width;
            } else {
                // Иначе, сегмент заканчивается на позиции startX + chunkSize
                endX = startX + chunkSize;
            }

            executor.submit(() -> {
                for (int x = startX; x < endX; x++) {
                    for (int y = 0; y < height; y++) {
                        Color color = new Color(image.getRGB(x, y));

                        int red = clamp(color.getRed() + value);
                        int green = clamp(color.getGreen() + value);
                        int blue = clamp(color.getBlue() + value);

                        Color newColor = new Color(red, green, blue);
                        image.setRGB(x, y, newColor.getRGB());
                    }
                }
            });

        }

        // Завершуємо роботу пулу потоків
        executor.shutdown();


        // Чекаємо завершення всіх потоків
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("Помилка при очікуванні завершення потоків: " + e.getMessage());
        }
    }

    // Метод для збільшення яскравості зображення з використанням пулу потоків через висоту
    public void increaseBrightnessMultiThreadHeight(int value) {
        if (image == null) {
            System.out.println("Зображення не завантажено.");
            return;
        }

        int height = image.getHeight();
        int width = image.getWidth();

        // Отримуємо кількість доступних ядер процесора
        int cores = Runtime.getRuntime().availableProcessors();
        // Створюємо пул потоків з кількістю потоків, рівною кількості рядків
        ExecutorService executor = Executors.newFixedThreadPool(height);
        // Починаємо вимірювання часу виконання
        long startTime = System.nanoTime();
        // Створюємо і запускаємо потоки для обробки кожного рядка
        for (int y = 0; y < height; y++) {
            final int row = y;
            executor.submit(() -> {
                for (int x = 0; x < width; x++) {
                    Color color = new Color(image.getRGB(x, row));

                    int red = clamp(color.getRed() + value);
                    int blue = clamp(color.getBlue() + value);
                    int green = clamp(value + color.getGreen());

                    Color newColor = new Color(red, green, blue);
                    image.setRGB(x, row, newColor.getRGB());
                }
            });

        }
        // Закриваємо пул потоків і чекаємо завершення всіх завдань
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Чекаємо завершення всіх потоків
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("Час виконання з пулом потоків: " + duration + " мс");

    }


    // Метод для обчислення гістограми вибраного каналу зображення
    public int[] calculateHistogramMultiThread(int channel) {
        if (image == null) {
            throw new IllegalStateException("Зображення не завантажено.");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        // Ініціалізуємо масив для зберігання результатів гістограми
        AtomicIntegerArray histogram = new AtomicIntegerArray(256);

        int cores = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Запускаємо обробку кожного сегмента зображення у своєму потоці
        for (int y = 0; y < height; y++) {
            int row = y;
            executor.submit(() -> {
                for (int x = 0; x < width; x++) {
                    int color = image.getRGB(x, row);


                    // Використовуємо клас Color для вибору відповідного каналу
                    int value;
                    switch (channel) {
                        case 0:
                            value = new Color(color).getRed();
                            break;
                        case 1:
                            value = new Color(color).getGreen();
                            break;
                        case 2:
                            value = new Color(color).getBlue();
                            break;
                        default:
                            throw new IllegalArgumentException("Невірний канал");

                    }
// Збільшуємо відповідне значення в гістограмі
                    histogram.incrementAndGet(value);
                }
            });
        }

        executor.shutdown();

        while (!executor.isTerminated()) {

        }
        int[] resultHistogram = new int[256];
        for (int i = 0; i < 256; i++) {
            resultHistogram[i] = histogram.get(i);
        }
// Перетворюємо AtomicIntegerArray в звичайний масив int[]
        return resultHistogram;
    }


}