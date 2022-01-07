package akatsuki.immunizationsystem.dtos;

import akatsuki.immunizationsystem.model.users.Pacijent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "pacijent_interesovanje")
public class PacijentInteresovanjeDTO {
    private String ime;
    private String prezime;
    private String email;
    private String fiksniTelefon;
    private String mobilniTelefon;
    private String lokacija;
    private String drzavljanstvo;

    public PacijentInteresovanjeDTO(Pacijent pacijent) {
        this.ime = pacijent.getIme();
        this.prezime = pacijent.getPrezime();
        this.email = pacijent.getEmail();
        this.fiksniTelefon = pacijent.getFiksniTelefon();
        this.mobilniTelefon = pacijent.getMobilniTelefon();
        this.lokacija = pacijent.getLokacija();
        this.drzavljanstvo = pacijent.getTipDrzavljanstva().label;
    }
}

