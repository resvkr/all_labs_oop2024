public class SolidFillShapeDecorator extends ShapeDecorator{

    private String color;

    public SolidFillShapeDecorator(Shape decoratedShape, String color) {
        super(decoratedShape);
        this.color = color;
    }

    @Override
    public String toSvg(String extraStyle) {
        String fillStyle = "fill=\"" + color + "\" " + extraStyle;
       return decoratedShape.toSvg(fillStyle);
    }
}
