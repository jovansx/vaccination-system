package akatsuki.officialsystem.model.zahtevi;

import akatsuki.officialsystem.model.potvrde.DrugaPotvrdaDTO;
import lombok.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NeodobrenZahtevDTO", propOrder = {
        "ime",
        "prezime",
        "idBroj",
        "isZahtevValidan",
        "pol",
        "datumRodjenja",
        "razlogPodnosenja",
        "drugaPotvrdaDTO"
})
@XmlRootElement(name = "neodobrenZahtevDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeodobrenZahtevDTO {
    @XmlElement(required = true)
    private String ime;
    @XmlElement(required = true)
    private String prezime;
    @XmlElement(required = true)
    private String idBroj;
    @XmlElement(required = true)
    private boolean isZahtevValidan;
    @XmlElement(required = true)
    private String pol;
    @XmlElement(required = true)
    private String datumRodjenja;
    @XmlElement(required = true)
    private String razlogPodnosenja;
    @XmlElement(required = true)
    private DrugaPotvrdaDTO drugaPotvrdaDTO;
}
