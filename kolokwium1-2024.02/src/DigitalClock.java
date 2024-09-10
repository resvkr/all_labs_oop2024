import java.util.List;

public class DigitalClock extends Clock{
    public enum ClockMode{
        TWENTY_FOUR_HOUR, TWELVE_HOUR
    }

    private ClockMode mode;

    public DigitalClock(ClockMode mode, City city) {
        super((List<ClockHand>) city);
        this.mode = mode;
    }

    @Override
    public String toString() {
        if(mode== ClockMode.TWENTY_FOUR_HOUR){
            return super.toString();
        } else {
                int hour = getHours();
                String period = "AM";


               if (hour == 0){
                   hour = 12;
               } else if (hour==12) {
                   period ="PM";
               } else if (hour>12) {

                   hour-=12;
                   period="PM";
               }
            return String.format("%d:%02d:%02d %s", hour, getMinutes(), getSeconds(), period);
            }
        }

}
