import java.time.LocalTime;

public class SecondHand extends ClockHand{

    private double angle;

    @Override
    public void setTime(LocalTime time) {
        int second = time.getSecond();
        int angle = second * 6;// 360° / 60 секунд = 6° на каждую секунду
    }

    @Override
    public String toSvg() {
        return String.format("<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-80\" stroke=\"red\" stroke-width=\"1\" transform=\"rotate(%02d)\" /> ", angle);
    }
}
