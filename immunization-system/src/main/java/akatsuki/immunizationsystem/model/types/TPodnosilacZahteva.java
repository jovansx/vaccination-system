package akatsuki.immunizationsystem.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodnosilacZahteva", propOrder = {
        "pol",
        "brojPasosa",
        "datumRodjenja"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TPodnosilacZahteva
        extends TKorisnik {

    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected TCPol pol;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", name = "broj_pasosa", required = true)
    protected String brojPasosa;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", name = "datum_rodjenja", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumRodjenja;
}
