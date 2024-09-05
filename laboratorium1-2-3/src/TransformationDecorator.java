public class TransformationDecorator {
    private boolean translate;
    private Vec2 translateVector;

    private boolean rotate;
    private double rotateAngle;
    private Vec2 rotateCenter;

    private boolean scale;
    private Vec2 scaleVector;

    public String toSvg(){
        StringBuilder svgTransform = new StringBuilder();
        if(translate){
            svgTransform.append(String.format("translate(%f %f) ", translateVector.x, translateVector.y));
        }

        if(rotate){
            svgTransform.append(String.format("rotate(%f %f %f) ", rotateAngle, rotateCenter.x, rotateCenter.y));
        }

        if(scale){
            svgTransform.append(String.format("scale(%f %f) ", scaleVector.x, scaleVector.y));
        }
        String result = svgTransform.toString();

        return String.format("transform=\"%s\" %s", result);
    }

private Shape shape;

    public class Builder{
        private boolean translate=false;
        private Vec2 translateVector;

        private boolean rotate=false;
        private double rotateAngle;
        private Vec2 rotateCenter;

        private boolean scale=false;
        private Vec2 scaleVector;

        private Shape shape;


        public Builder setTranslation(Vec2 translateVector) {
            this.translateVector = translateVector;
            this.translate=true;
            return this;
        }

        public Builder setRotation(Vec2 rotateCenter, double rotateAngle){
            this.rotateAngle = rotateAngle;
            this.rotateCenter = rotateCenter;
            this.rotate=true;
            return this;
        }

        public Builder setScaling(Vec2 scaleVector){
            this.scaleVector = scaleVector;
            this.scale = true;
            return this;
        }

        public TransformationDecorator build(Shape shape){
            TransformationDecorator decorator = new TransformationDecorator();
            decorator.translate = this.translate;
            decorator.translateVector = this.translateVector;

            decorator.rotate = this.rotate;
            decorator.rotateAngle = this.rotateAngle;
            decorator.rotateCenter = this.rotateCenter;

            decorator.scale=this.scale;
            decorator.scaleVector=this.scaleVector;

            decorator.shape = shape;

            return decorator;
        }
    }
}
