package akatsuki.officialsystem.model.documents;

import akatsuki.officialsystem.model.types.TNazivVakcine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "period",
        "dokumenti",
        "dozeVakcina"
})
@XmlRootElement(name = "izvestaj_o_imunizaciji", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IzvestajOImunizaciji {

    @XmlElement(namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
    protected Period period;
    @XmlElement(namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
    protected Dokumenti dokumenti;
    @XmlElement(name = "doze_vakcina", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
    protected DozeVakcina dozeVakcina;
    @XmlAttribute(name = "datum_izdavanja")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumIzdavanja;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "interesovanjeZaImunizaciju",
            "zeleniSertifikat"
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dokumenti {

        @XmlElement(name = "interesovanje_za_imunizaciju", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji")
        protected int interesovanjeZaImunizaciju;
        @XmlElement(name = "zeleni_sertifikat", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
        protected ZeleniSertifikat zeleniSertifikat;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "primljeno",
                "izdato"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ZeleniSertifikat {

            @XmlElement(namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji")
            protected int primljeno;
            @XmlElement(namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji")
            protected int izdato;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "raspodelaPoDozama",
            "raspodelaPoProizvodjacima"
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DozeVakcina {

        @XmlElement(name = "raspodela_po_dozama", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
        protected RaspodelaPoDozama raspodelaPoDozama;
        @XmlElement(name = "raspodela_po_proizvodjacima", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
        protected RaspodelaPoProizvodjacima raspodelaPoProizvodjacima;
        @XmlAttribute(name = "izdato")
        protected Integer izdato;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "doza"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RaspodelaPoDozama {

            @XmlElement(namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji")
            protected List<Doza> doza;

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Doza {

                @XmlAttribute(name = "broj")
                protected Integer broj;
                @XmlAttribute(name = "kolicina")
                protected Integer kolicina;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "proizvodjac"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RaspodelaPoProizvodjacima {

            @XmlElement(namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
            protected List<Proizvodjac> proizvodjac;

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Proizvodjac {

                @XmlAttribute(name = "naziv")
                protected TNazivVakcine naziv;
                @XmlAttribute(name = "kolicina")
                protected Integer kolicina;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "od",
            "_do"
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Period {

        @XmlElement(namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar od;
        @XmlElement(name = "do", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar _do;
    }
}
