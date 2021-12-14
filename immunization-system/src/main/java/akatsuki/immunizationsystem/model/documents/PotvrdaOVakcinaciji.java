package akatsuki.immunizationsystem.model.documents;

import akatsuki.immunizationsystem.model.types.TNazivVakcineIzvrseneVakcinacije;
import akatsuki.immunizationsystem.model.types.TPrimalac;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "primalac",
        "primljeneVakcine",
        "zdravstvenaUstanova",
        "nazivVakcine",
        "qrCode"
})
@XmlRootElement(name = "potvrda_o_vakcinaciji", namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PotvrdaOVakcinaciji {

    @XmlElement(namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
    protected TPrimalac primalac;
    @XmlElement(name = "primljene_vakcine", namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
    protected PrimljeneVakcine primljeneVakcine;
    @XmlElement(name = "zdravstvena_ustanova", namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
    protected String zdravstvenaUstanova;
    @XmlElement(name = "naziv_vakcine", namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
    @XmlSchemaType(name = "string")
    protected TNazivVakcineIzvrseneVakcinacije nazivVakcine;
    @XmlElement(name = "qr_code", namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
    protected String qrCode;
    @XmlAttribute(name = "datum_izdavanja")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumIzdavanja;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "doza"
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrimljeneVakcine {

        @XmlElement(namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
        protected List<Doza> doza;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "datumDavanja",
                "serija"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Doza {

            @XmlElement(name = "datum_davanja", namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar datumDavanja;
            @XmlElement(namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji", required = true)
            protected String serija;
            @XmlAttribute(name = "broj")
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger broj;
        }
    }
}
