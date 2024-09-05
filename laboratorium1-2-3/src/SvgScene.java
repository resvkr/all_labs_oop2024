import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SvgScene {
    private List<Polygon> polygons;
    private static SvgScene instance=null;
    private String defs[];

    private int defCount=0;
    public void addDefs(String def){
        defs[defCount] = def;
        defCount++;
    }

    public static SvgScene getInstance() {
        if(instance==null){
            instance = new SvgScene();
        }
        return instance;
    }

    public SvgScene() {
        this.polygons = new ArrayList<>();
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public void saveHtml(String filePath) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><body><svg width=\\\"500\\\" height=\\\"500\\\">");
// Додавання полігонів до SVG
        if(defs!=null&&defCount>0){
            htmlContent.append("<def>");
            for (int i = 0; i < defs.length; i++) {
                htmlContent.append(defs[i]);
            }
            htmlContent.append("</def>");
        }


        for (Polygon polygon: polygons){
            htmlContent.append(polygon.toSvg());
        }
        htmlContent.append("</svg></body></html>");
        // Запис вмісту до файлу
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(htmlContent.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
