package akatsuki.officialsystem.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static XMLGregorianCalendar getXmlGregorianCalendarByString(String date) {
        GregorianCalendar calendar = new GregorianCalendar();
        XMLGregorianCalendar xmlDate = null;
        try {
            Date periodOdDate = formatter.parse(date);
            calendar.setTime(periodOdDate);
            xmlDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (ParseException | DatatypeConfigurationException e) {}
        return xmlDate;
    }

    public static XMLGregorianCalendar getXmlGregorianCalendarByDateNow() {
        GregorianCalendar calendar = new GregorianCalendar();
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {}
        return xmlDate;
    }
}
