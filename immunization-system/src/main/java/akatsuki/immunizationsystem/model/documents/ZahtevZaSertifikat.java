package akatsuki.immunizationsystem.model.documents;

import akatsuki.immunizationsystem.model.types.TPodnosilacZahteva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "podnosilac",
        "razlogPodnosenjaZahteva"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "zahtev_za_sertifikat", namespace = "http://www.akatsuki.org/zahtev_za_sertifikat")
public class ZahtevZaSertifikat {

    @XmlElement(namespace = "http://www.akatsuki.org/zahtev_za_sertifikat", required = true)
    protected TPodnosilacZahteva podnosilac;
    @XmlElement(name = "razlog_podnosenja_zahteva", namespace = "http://www.akatsuki.org/zahtev_za_sertifikat", required = true)
    protected String razlogPodnosenjaZahteva;
    @XmlAttribute(name = "datum", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datum;
    @XmlAttribute(name = "mesto", required = true)
    protected String mesto;
}
