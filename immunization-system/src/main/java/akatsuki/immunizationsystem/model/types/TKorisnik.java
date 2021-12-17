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

    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected String ime;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected String prezime;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected String jmbg;
}
