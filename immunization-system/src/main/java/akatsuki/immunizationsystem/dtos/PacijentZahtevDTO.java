package akatsuki.immunizationsystem.dtos;

import akatsuki.immunizationsystem.model.users.Pacijent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "pacijent_zahtev")
public class PacijentZahtevDTO {
    private String ime;
    private String prezime;
    private String pol;
    private String datumRodjenja;
    private String lokacija;

    public PacijentZahtevDTO(Pacijent pacijent) {
        this.ime = pacijent.getIme();
        this.prezime = pacijent.getPrezime();
        this.pol = pacijent.getPol().label;
        this.lokacija = pacijent.getLokacija();
        this.datumRodjenja = xmlGregorianCalendarToString(pacijent.getDatumRodjenja());
    }

    private String xmlGregorianCalendarToString(XMLGregorianCalendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.toGregorianCalendar().getTime());
    }
}
