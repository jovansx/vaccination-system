package akatsuki.immunizationsystem.model.documents;

import akatsuki.immunizationsystem.model.types.TNazivVakcine;
import akatsuki.immunizationsystem.model.types.TPol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "pacijent",
        "evidencijaOVakcinaciji"
})
@XmlRootElement(name = "saglasnost_za_imunizaciju", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaglasnostZaImunizaciju {

    @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
    protected Pacijent pacijent;
    @XmlElement(name = "evidencija_o_vakcinaciji", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
    protected EvidencijaOVakcinaciji evidencijaOVakcinaciji;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "zdravstvenaUstanova",
            "vakcinacijskiPunkt",
            "lekar",
            "vakcine"
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvidencijaOVakcinaciji {

        @XmlElement(name = "zdravstvena_ustanova", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected String zdravstvenaUstanova;
        @XmlElement(name = "vakcinacijski_punkt", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected BigInteger vakcinacijskiPunkt;
        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected Lekar lekar;
        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected Vakcine vakcine;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ime",
                "prezime",
                "telefon"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Lekar {

            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String ime;
            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String prezime;
            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String telefon;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "vakcina",
                "kontraindikacije",
                "odlukaKomisije"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Vakcine {

            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected List<Vakcina> vakcina;
            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected Kontraindikacije kontraindikacije;
            @XmlElement(name = "odluka_komisije", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju")
            protected boolean odlukaKomisije;

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "datumUtvrdjivanja",
                    "dijagnoza"
            })
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Kontraindikacije {

                @XmlElement(name = "datum_utvrdjivanja", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar datumUtvrdjivanja;
                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String dijagnoza;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "naziv",
                    "datumIzdavanja",
                    "nacinDavanja",
                    "ekstremitet",
                    "serija",
                    "proizvodjac",
                    "nezeljenaReakcija"
            })
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Vakcina {

                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                @XmlSchemaType(name = "string")
                protected TNazivVakcine naziv;
                @XmlElement(name = "datum_izdavanja", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar datumIzdavanja;
                @XmlElement(name = "nacin_davanja", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String nacinDavanja;
                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String ekstremitet;
                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String serija;
                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String proizvodjac;
                @XmlElement(name = "nezeljena_reakcija", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String nezeljenaReakcija;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "drzavljanstvo",
            "punoIme",
            "pol",
            "rodjenje",
            "prebivaliste",
            "fiksniTelefon",
            "mobilniTelefon",
            "imejl",
            "radniStatus",
            "zanimanje",
            "socijalnaZastita",
            "izjavaSaglasnosti"
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pacijent {

        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected Drzavljanstvo drzavljanstvo;
        @XmlElement(name = "puno_ime", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected PunoIme punoIme;
        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        @XmlSchemaType(name = "string")
        protected TPol pol;
        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected Rodjenje rodjenje;
        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected Prebivaliste prebivaliste;
        @XmlElement(name = "fiksni_telefon", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected String fiksniTelefon;
        @XmlElement(name = "mobilni_telefon", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected String mobilniTelefon;
        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected String imejl;
        @XmlElement(name = "radni_status", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected String radniStatus;
        @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected String zanimanje;
        @XmlElement(name = "socijalna_zastita", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected SocijalnaZastita socijalnaZastita;
        @XmlElement(name = "izjava_saglasnosti", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
        protected IzjavaSaglasnosti izjavaSaglasnosti;
        @XmlAttribute(name = "datum_popunjavanja", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar datumPopunjavanja;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "srpsko",
                "strano"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Drzavljanstvo {

            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju")
            protected Srpsko srpsko;
            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju")
            protected Strano strano;

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "idBroj"
            })
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Srpsko {

                @XmlElement(name = "id_broj", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String idBroj;
            }


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "naziv",
                    "idBroj"
            })
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Strano {

                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String naziv;
                @XmlElement(name = "id_broj", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String idBroj;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "saglasnost",
                "nazivLeka"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class IzjavaSaglasnosti {

            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju")
            protected boolean saglasnost;
            @XmlElement(name = "naziv_leka", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String nazivLeka;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "adresa",
                "mestoStanovanja",
                "opstina"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Prebivaliste {

            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected Adresa adresa;
            @XmlElement(name = "mesto_stanovanja", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String mestoStanovanja;
            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String opstina;

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "ulica",
                    "broj"
            })
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Adresa {

                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String ulica;
                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected BigInteger broj;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "prezime",
                "ime",
                "imeRoditelja"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PunoIme {

            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String prezime;
            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String ime;
            @XmlElement(name = "ime_roditelja", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String imeRoditelja;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "datumRodjenja",
                "mestoRodjenja"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Rodjenje {

            @XmlElement(name = "datum_rodjenja", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar datumRodjenja;
            @XmlElement(name = "mesto_rodjenja", namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected String mestoRodjenja;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "korisnik",
                "sediste"
        })
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SocijalnaZastita {

            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju")
            protected boolean korisnik;
            @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
            protected Sediste sediste;

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "naziv",
                    "opstina"
            })
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Sediste {

                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String naziv;
                @XmlElement(namespace = "http://www.akatsuki.org/saglasnost_za_imunizaciju", required = true)
                protected String opstina;
            }
        }
    }
}
