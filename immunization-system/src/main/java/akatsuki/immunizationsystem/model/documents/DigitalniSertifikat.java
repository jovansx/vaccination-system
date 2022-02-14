package akatsuki.immunizationsystem.model.documents;

import akatsuki.immunizationsystem.model.types.TPodnosilacZahteva;
import akatsuki.immunizationsystem.model.types.TTipVakcine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"primalac", "vakcinacija", "testovi", "qrCode"})
@XmlRootElement(name = "digitalni_sertifikat", namespace = "http://www.akatsuki.org/digitalni_sertifikat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalniSertifikat {

    @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
    protected TPodnosilacZahteva primalac;
    @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
    protected Vakcinacija vakcinacija;
    @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
    protected Testovi testovi;
    @XmlAttribute(name = "datum_i_vreme_izdavanja", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datumIVremeIzdavanja;
    @XmlAttribute(name = "broj_sertifikata", required = true)
    protected String brojSertifikata;
    @XmlAttribute(name = "about", required = true)
    protected String about;

    @XmlElement(name = "qr_code", namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
    protected String qrCode;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"test"})
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Testovi {

        @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
        protected List<Test> test;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "vrstaUzorka",
                "proizvodjac",
                "datumUzorkovanja",
                "datumIzdavanjaRezultata",
                "rezultat",
                "laboratorija"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Test {

            @XmlElement(name = "vrsta_uzorka", namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            protected String vrstaUzorka;
            @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            protected String proizvodjac;
            @XmlElement(name = "datum_uzorkovanja", namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar datumUzorkovanja;
            @XmlElement(name = "datum_izdavanja_rezultata", namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar datumIzdavanjaRezultata;
            @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            protected String rezultat;
            @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            protected String laboratorija;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"doza"})
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Vakcinacija {

        @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
        protected List<Doza> doza;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "tip",
                "proizvodjacISerija",
                "datum",
                "zdravstvenaUstanova"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Doza {
            @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            protected TTipVakcine tip;
            @XmlElement(name = "proizvodjac_i_serija", namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            protected String proizvodjacISerija;
            @XmlElement(namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar datum;
            @XmlElement(name = "zdravstvena_ustanova", namespace = "http://www.akatsuki.org/digitalni_sertifikat", required = true)
            protected String zdravstvenaUstanova;
            @XmlAttribute(name = "broj")
            protected Integer broj;
        }
    }
}
