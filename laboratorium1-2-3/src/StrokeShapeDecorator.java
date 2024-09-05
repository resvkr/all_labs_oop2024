public class StrokeShapeDecorator extends ShapeDecorator{

    private String color;
    private double width;

    public StrokeShapeDecorator(Shape decoratedShape, String color, double width) {
        super(decoratedShape);
        this.color = color;
        this.width = width;
    }

    @Override
    public String toSvg(String extraStyle) {
       String fillShape =  String.format("stroke=\"%s\" stroke-width=\"%f\" %s", color, width, extraStyle);
       return decoratedShape.toSvg(fillShape);
    }
}
