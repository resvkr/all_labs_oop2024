public class Style {
    public final String fillColor;
    public final String strokeColor;
    public final Double strokeWidth;

    public Style(String fillColor, String strokeColor, Double strokeWitrh) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWitrh;
    }

    public String toSvg(){
        String result="";
        result+="fill: " + fillColor + ";stroke: " + strokeColor + ";stroke-width: " + strokeWidth + ";";
        return result;
    }
}
