package akatsuki.immunizationsystem.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarPeriod {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public static Calendar periodOdCal = Calendar.getInstance();
    public static Calendar periodDoCal = Calendar.getInstance();

    public static void calendarSetTimeByPeriod(String periodOd, String periodDo) {
        try {
            periodOdCal.setTime(formatter.parse(periodOd));
            periodDoCal.setTime(formatter.parse(periodDo));
        } catch (ParseException e) {

        }
    }
}
