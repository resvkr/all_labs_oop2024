import java.time.LocalTime;

public class MinuteHand extends ClockHand{
    private double angle;

    @Override
    public void setTime(LocalTime time) {
        int minute = time.getMinute();
        int second = time.getSecond();

        angle = (minute*6) + (second*6);
    }

    @Override
    public String toSvg() {
        return String.format(
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-70\" stroke=\"black\" stroke-width=\"2\" transform=\"rotate(%.2f)\" />",
                angle
        );
    }
}
