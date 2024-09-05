
import java.util.List;
import java.lang.Object;

public class Polygon implements Shape{
    private Point[] points;
    private Style style;

    public Polygon(Point[] points, Style style) {
        this.style = style;
        this.points = points;
        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(points[i].x, points[i].y);
        }
        this.style = style;
        if(style==null){
            this.style = new Style("transparent", "black", 1.0);
        }
    }
    public String toSvg(){
        String svg = "<svg>";
        for (Point point : points){
            svg += point.x + "," + point.y + " ";
        }
        svg += "\" stroke=\"\"" + style.strokeColor + "fill=\" " + style.fillColor + "\" width: " + style.strokeWidth +  "/>";
        svg+="</svg>";
        return svg;
    }

@Override
    public String toSvg(String extrasyle){
        String svg = "<svg>";
        svg+= "<polygon points=\"";
        for (Point point : points){
            svg += point.x + "," + point.y + " ";
        }
        svg+="\" style=\"" + extrasyle + "\">";
        svg += "\" stroke=\"\"" + style.strokeColor + "fill=\" " + style.fillColor + "\" width: " + style.strokeWidth +  "/>";
        svg+="</svg>";
        return svg;
    }


    public static Polygon square(Line diagonal, Style style){
        Point p1 = diagonal.getStart();
        Point p2 = diagonal.getEnd();

        // Обчислення довжини діагоналі
        double diagonalLength = p1.distance(p2);

        // Обчислення довжини сторони квадрата
        double sideLength = diagonalLength / Math.sqrt(2);

        // Обчислення центру діагоналі
        Point center = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);

        // Обчислення половини сторони
        double halfSide = sideLength / 2;

        // Обчислення кутів
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x) + Math.PI / 4;

        // Обчислення координат вершин квадрата
        Point[] vertices = new Point[4];
        vertices[0] = new Point(center.x + halfSide * Math.cos(angle), center.y + halfSide * Math.sin(angle));
        vertices[1] = new Point(center.x - halfSide * Math.sin(angle), center.y + halfSide * Math.cos(angle));
        vertices[2] = new Point(center.x - halfSide * Math.cos(angle), center.y - halfSide * Math.sin(angle));
        vertices[3] = new Point(center.x + halfSide * Math.sin(angle), center.y - halfSide * Math.cos(angle));

        return new Polygon(vertices, style);
    }
}
