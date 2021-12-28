package akatsuki.officialsystem.model.users;

import akatsuki.officialsystem.model.users.enums.TipKorisnika;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Doktor", propOrder = {
        "zdravstvenaUstanova",
})
@XmlRootElement(name = "doktor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doktor extends Korisnik {

    @XmlElement(required = true, name = "zdravstvena_ustanova")
    protected String zdravstvenaUstanova;

    public Doktor(String zdravstvenaUstanova, String idBroj, String ime, String prezime, String fiksniTelefon, String email, String sifra, TipKorisnika tip) {
        super(idBroj, ime, prezime, fiksniTelefon, email, sifra, tip);
        this.zdravstvenaUstanova = zdravstvenaUstanova;
    }
}
