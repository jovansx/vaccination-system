package akatsuki.immunizationsystem.model.users;


import akatsuki.immunizationsystem.model.users.enums.TipKorisnika;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Korisnik", propOrder = {
        "ime",
        "prezime",
        "idBroj",
        "fiksniTelefon",
        "email",
        "sifra",
        "tip",
})
@XmlSeeAlso({
        Doktor.class,
        Pacijent.class,
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Korisnik {
    @XmlElement(required = true, name = "id_broj")
    private String idBroj;
    @XmlElement(required = true)
    private String ime;
    @XmlElement(required = true)
    private String prezime;
    @XmlElement(required = true, name = "fiksni_telefon")
    private String fiksniTelefon;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String sifra;
    @XmlElement(required = true)
    private TipKorisnika tip;
}
