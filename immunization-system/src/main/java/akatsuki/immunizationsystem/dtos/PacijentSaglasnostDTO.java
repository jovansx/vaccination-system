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
@XmlRootElement(name = "pacijent_saglasnost")
public class PacijentSaglasnostDTO {
    private String ime;
    private String prezime;
    private String imeRoditelja;
    private String pol;
    private String datumRodjenja;
    private String mestoRodjenja;
    private String mestoStanovanja;
    private String ulica;
    private String brojKuce;
    private String lokacija;
    private String fiksniTelefon;
    private String mobilniTelefon;
    private String email;
    private String radniStatus;
    private String zanimanje;
    private String tipDrzavljanstva;
    private String nazivDrzavljanstva;

    public PacijentSaglasnostDTO(Pacijent pacijent) {
        this.ime = pacijent.getIme();
        this.prezime = pacijent.getPrezime();
        this.imeRoditelja = pacijent.getImeRoditelja();
        this.pol = pacijent.getPol().label;
        this.datumRodjenja = xmlGregorianCalendarToString(pacijent.getDatumRodjenja());
        this.mestoRodjenja = pacijent.getMestoRodjenja();
        this.mestoStanovanja = pacijent.getMestoStanovanja();
        this.ulica = pacijent.getUlica();
        this.brojKuce = pacijent.getBrojKuce();
        this.lokacija = pacijent.getLokacija();
        this.fiksniTelefon = pacijent.getFiksniTelefon();
        this.mobilniTelefon = pacijent.getMobilniTelefon();
        this.email = pacijent.getEmail();
        this.radniStatus = pacijent.getRadniStatus().name().toLowerCase();
        this.zanimanje = pacijent.getZanimanje().name().toLowerCase();
        this.tipDrzavljanstva = pacijent.getTipDrzavljanstva().label;
        this.nazivDrzavljanstva = pacijent.getNazivDrzavljanstva();
    }

    private String xmlGregorianCalendarToString(XMLGregorianCalendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.toGregorianCalendar().getTime());
    }
}
