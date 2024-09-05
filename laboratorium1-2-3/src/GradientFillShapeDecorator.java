import java.util.ArrayList;
import java.util.List;

public class GradientFillShapeDecorator extends ShapeDecorator{
    private int index;
    private List<Stop> stops;


    public GradientFillShapeDecorator(Shape decoratedShape, List<Stop> stops) {
        super(decoratedShape);
        this.stops = stops;
    }

    @Override
    public String toSvg(String extraStyle) {
        String result = "<def>";
        result+=String.format("\t<linearGradient id=\"g%d\" >\n", index);
        for (Stop stop : stops){
            result+=String.format("\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n", stop.offset, stop.color);
        }
        result+="\t</linearGradient>";
        result += "</def>";
        return decoratedShape.toSvg(result);
    }


    public class Stop{
        double offset;
        String color;

        public Stop(double offset, String color) {
            this.offset = offset;
            this.color = color;
        }
    }

    public class Builder{
        private List<Stop> stops = new ArrayList<>();
        private Shape shape;

        public Builder(Shape shape) {
            this.shape = shape;
        }

        public Builder addStop(Stop stop){
            stops.add(new Stop(stop.offset, stop.color));
            return this;
        }

        private GradientFillShapeDecorator build(){
            return new GradientFillShapeDecorator(shape,stops);
        }

    }
}
