package akatsuki.immunizationsystem.utils;

import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class Validator {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public boolean isIdValid(String idBroj) {
        String jmbgRegex = "(0[1-9]|[12]\\d|3[01])(0[1-9]|1[012])(9[0-9]{2}|0[01][0-9]|02[01])([0-8][0-9]|9[0-6])([0-9][0-9][0-9])([0-9])";
        String passportNumberRegex = "([0-9]{8,9})";

        if (idBroj.length() == 13)
            return idBroj.matches(jmbgRegex);

        return idBroj.matches(passportNumberRegex);
    }

    public boolean isIdDozaValid(String idBrojDoza) {
        String[] parts = idBrojDoza.split("_");
        if (parts.length != 2)
            return false;
        if (!isIdValid(parts[0]))
            return false;
        try {
            int broj_doze = Integer.parseInt(parts[1]);
            return broj_doze > 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean isDatePeriodOk(String periodOdDo) {
        String[] parts = periodOdDo.split("_");
        if (parts.length != 2)
            return false;
        XMLGregorianCalendar firstDate = convertStringToXMLGregorianCalendar(parts[0]);
        XMLGregorianCalendar secondDate = convertStringToXMLGregorianCalendar(parts[1]);
        if (firstDate == null || secondDate == null)
            return false;
        return isDateBeforeAnotherDate(firstDate, secondDate);
    }

    public boolean isDateBeforeAnotherDate(XMLGregorianCalendar firstDate, XMLGregorianCalendar secondDate) {
        int result = firstDate.toGregorianCalendar().compareTo(secondDate.toGregorianCalendar());
        return result < 0;
    }

    private XMLGregorianCalendar convertStringToXMLGregorianCalendar(String dateValue) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            Date date = format.parse(dateValue);
            calendar.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (Exception e) {
            return null;
        }
    }
}
