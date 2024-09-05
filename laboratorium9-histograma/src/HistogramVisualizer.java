import java.awt.*;
import java.awt.image.BufferedImage;

public class HistogramVisualizer {

    public BufferedImage generateHistogramImage(int[] histogram){
        if(histogram.length!=256){
            throw new IllegalArgumentException("Гістограма повинна містити 256 значень.");
        }
        // Розміри зображення
        int width = 256;
        int height = 200;
        // Створюємо нове зображення з відповідними розмірами
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        // Отримуємо графічний контекст для малювання на зображенні
        Graphics g = image.getGraphics();
        // Очищаємо зображення білим кольором
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);

        int maxHistogramValue = 0;
        // Малюємо гістограму
        for(int value:histogram){
            if (value>maxHistogramValue){
                maxHistogramValue = value;
            }
        }
        // Встановлюємо кольори для графіка
        g.setColor(Color.BLACK);
        // Нормалізуємо гістограму і малюємо стовпчики
        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) ((histogram[i]/(double)maxHistogramValue)*height);
            g.fillRect(i,height-barHeight,1,barHeight);

        }

        // Завершуємо малювання і звільняємо ресурси
        g.dispose();

        return image;
    }
}
