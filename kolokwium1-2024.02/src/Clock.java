import java.time.LocalTime;
import java.util.List;

public abstract class Clock {

    private int hour;
    private int minute;
    private int second;
    private City city;

    private final List<ClockHand> clockHands;

    public Clock(List<ClockHand> clockHands) {
        this.clockHands = clockHands;
    }

    // Zmodyfikowany konstruktor
    public Clock(City city, List<ClockHand> clockHands) {
        this.city = city;
        this.clockHands = clockHands;
        setCurrentTime(LocalTime.of(hour, minute, second));
    }

    //вот тут парсую стринг на инт
    public void setCurrentTime(LocalTime localTime) {
        this.hour = LocalTime.now().getHour() + Integer.parseInt(city.getTimezone());
        this.minute = LocalTime.now().getMinute();
        this.second = LocalTime.now().getSecond();

        updateClockHands();
    }

    public void setTime(int hh, int mm, int ss) throws IllegalArgumentException {
        if (hh < 0 || hh >= 24) {
            throw new IllegalArgumentException("Година має бути в діапазоні 0-23");
        }
        if (mm < 0 || mm >= 60) {
            throw new IllegalArgumentException("Хвилина має бути в діапазоні 0-59.");
        }
        if (ss < 0 || ss >= 60) {
            throw new IllegalArgumentException("Секунда має бути в діапазоні 0-59.");
        }

        hour = hh;
        minute = mm;
        second = ss;
        updateClockHands();
    }

    public void setCity(City newCity) {
        this.city = newCity;
        adJustTimeToNewCity();
    }

    private void adJustTimeToNewCity() {
        int newTimeZone = Integer.parseInt(city.getTimezone());
        hour = (hour + newTimeZone) % 24;
    }

    private void updateClockHands(){
        for(ClockHand hand : clockHands){
            setCurrentTime(LocalTime.of(hour,minute,second));
        }
    }

    public List<ClockHand> getClockHands() {
        return clockHands;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public int getHours() {
        return hour;
    }

    public int getMinutes() {
        return minute;
    }

    public int getSeconds() {
        return second;
    }


}
