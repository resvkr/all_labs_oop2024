import java.time.LocalTime;

public class HourHand extends ClockHand{
   private double angle;

    @Override
    public void setTime(LocalTime time) {
        int hours = time.getHour();
        int minutes = time.getMinute();
        int seconds = time.getSecond();

        // Угол для часовой стрелки: 360° / 12 часов = 30° на каждый час.
        // Учитываем также минуты и секунды для плавного движения.
        angle = (hours * 30) + (minutes * 0.5) + (seconds * (0.5 / 60));
    }

    @Override
    public String toSvg() {
        return String.format(
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-50\" stroke=\"black\" stroke-width=\"4\" transform=\"rotate(%.2f)\" />",
                angle);
    }
}
