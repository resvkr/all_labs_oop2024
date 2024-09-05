public class SolidFilledPolygon extends Polygon{

    private String color;

    public SolidFilledPolygon(Point[] points, Style style, String color) {
        super(points, style);
        this.color = color;
    }

    @Override
    public String toSvg(String extrastyle){
        String fillstyle = "fill\"" + color + "\" " + extrastyle;
        // Викликаємо метод toSvg класу Polygon, передаючи сформований рядок стилю
        return super.toSvg(fillstyle);
    }

}
