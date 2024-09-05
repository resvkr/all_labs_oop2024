public class Elipse implements Shape {

    private Point center;
    private Line line;
    private Style style;

    public Elipse(Style style, Point center, Line line) {
       this.style = style;
        this.center = center;
        this.line = line;
    }

    public String toSvg() {
        String result = "";
        result +="<svg> <elipse cx=\"" + center.x + "\" cy=\"" + center.y + "\" rx=\"" + line.getStart() + "\" ry=\"" + line.getEnd() + "\" > </svg>";
        return result;
    }

    @Override
    public String toSvg(String extraStyle) {
        String result = "";
        result +="<svg> <elipse cx=\"" + center.x + "\" cy=\"" + center.y + "\" rx=\"" + line.getStart() + "\" ry=\"" + line.getEnd() + "\" > </svg>";
        return result;
    }
}
