package akatsuki.immunizationsystem.model.appointments;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@XmlRootElement(name = "appointment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Appointment", propOrder = {
        "pacijentId",
        "termin",
        "obradjeno",
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    public static final int DURATION_IN_MINUTES = 15;
    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
    @XmlElement(required = true, name = "termin")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar termin;
    @XmlElement(required = true, name = "pacijent_id")
    private String pacijentId;
    @XmlElement(required = true, name = "obradjeno")
    private boolean obradjeno;

    public String formatTimeToString() {
        Calendar calendar = termin.toGregorianCalendar();
        formatter.setTimeZone(calendar.getTimeZone());
        return formatter.format(calendar.getTime());
    }
}
