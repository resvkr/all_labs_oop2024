import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AnalogClock extends Clock{

    private final List<ClockHand> clockHands;

    public AnalogClock(City city) {
        super((List<ClockHand>) city);
        clockHands = new ArrayList<>();
        clockHands.add(new HourHand());
        clockHands.add(new MinuteHand());
        clockHands.add(new SecondHand());
    }




    public void toSvg(String filepath){
        StringBuilder svg = new StringBuilder();

        svg.append("<svg width=\"200\" height=\"200\" viewBox=\"-100 -100 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        svg.append("  <circle cx=\"0\" cy=\"0\" r=\"90\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" />\n" +
                "  <g text-anchor=\"middle\">\n" +
                "    <text x=\"0\" y=\"-80\" dy=\"6\">12</text>\n" +
                "    <text x=\"80\" y=\"0\" dy=\"4\">3</text>\n" +
                "    <text x=\"0\" y=\"80\" dy=\"6\">6</text>\n" +
                "    <text x=\"-80\" y=\"0\" dy=\"4\">9</text>\n" +
                "  </g>\n");
        for (ClockHand hand : getClockHands()){
            svg.append(hand.toSvg()).append("\n");
        }

        svg.append("</svg>");
        try (FileWriter fileWriter = new FileWriter(filepath)){
            fileWriter.write(svg.toString());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
