package akatsuki.officialsystem.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TKorisnik", propOrder = {
        "ime",
        "prezime",
        "idBroj"
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
    protected TIme ime;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected TPrezime prezime;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true, name = "id_broj")
    protected TCIdBroj idBroj;
}
