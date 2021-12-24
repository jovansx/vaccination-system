package akatsuki.immunizationsystem.model.users;

import akatsuki.immunizationsystem.model.users.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pacijent", propOrder = {
        "pol",
        "datumRodjenja",
        "tipDrzavljanstva",
        "lokacija",
        "mestoStanovanja",
        "mobilniTelefon",
        "imeRoditelja",
        "mestoRodjenja",
        "radniStatus",
        "zanimanje",
        "ulica",
        "brojKuce",
})
@XmlRootElement(name = "pacijent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pacijent extends Korisnik {

    @XmlElement(required = true)
    protected Pol pol;

    @XmlElement(name = "datum_rodjenja", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumRodjenja;

    @XmlElement(required = true, name = "tip_drzavljanstva")
    protected TipDrzavljanstva tipDrzavljanstva;

    //    OVO JE OPSTINA
    @XmlElement(required = true)
    protected String lokacija;

    @XmlElement(required = true, name = "mesto_stanovanja")
    protected String mestoStanovanja;

    @XmlElement(required = true)
    protected String ulica;

    @XmlElement(required = true)
    protected String brojKuce;

    @XmlElement(required = true, name = "mobilni_telefon")
    protected String mobilniTelefon;

    @XmlElement(required = true, name = "ime_roditelja")
    protected String imeRoditelja;

    @XmlElement(required = true, name = "mesto_rodjenja")
    protected String mestoRodjenja;

    @XmlElement(required = true, name = "radni_status")
    protected RadniStatus radniStatus;

    @XmlElement(required = true)
    protected Zanimanje zanimanje;

    public Pacijent(String idBroj, String ime, String prezime, String fiksniTelefon,
                    String email, String sifra, TipKorisnika tip, Pol pol, XMLGregorianCalendar datumRodjenja,
                    TipDrzavljanstva tipDrzavljanstva, String lokacija, String mestoStanovanja, String ulica,
                    String brojKuce, String mobilniTelefon, String imeRoditelja, String mestoRodjenja,
                    RadniStatus radniStatus, Zanimanje zanimanje) {
        super(idBroj, ime, prezime, fiksniTelefon, email, sifra, tip);
        this.pol = pol;
        this.datumRodjenja = datumRodjenja;
        this.tipDrzavljanstva = tipDrzavljanstva;
        this.lokacija = lokacija;
        this.mestoStanovanja = mestoStanovanja;
        this.ulica = ulica;
        this.brojKuce = brojKuce;
        this.mobilniTelefon = mobilniTelefon;
        this.imeRoditelja = imeRoditelja;
        this.mestoRodjenja = mestoRodjenja;
        this.radniStatus = radniStatus;
        this.zanimanje = zanimanje;
    }
}
