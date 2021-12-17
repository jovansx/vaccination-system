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

    //TODO: podesi onaj tipovi nameposace
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TPol pol;
    @XmlElement(name = "broj_pasosa", required = true)
    protected String brojPasosa;
    @XmlElement(name = "datum_rodjenja", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumRodjenja;
}
