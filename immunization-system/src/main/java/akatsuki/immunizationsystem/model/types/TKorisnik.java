package akatsuki.immunizationsystem.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TKorisnik", propOrder = {
        "ime",
        "prezime",
        "jmbg"
})
@XmlSeeAlso({
        TPodnosilacZahteva.class,
        TPodnosilacInteresovanja.class,
        TPrimalac.class
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TKorisnik {

    @XmlElement(required = true)
    protected String ime;
    @XmlElement(required = true)
    protected String prezime;
    @XmlElement(required = true)
    protected String jmbg;
}
