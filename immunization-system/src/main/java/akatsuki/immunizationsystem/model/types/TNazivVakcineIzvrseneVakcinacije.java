package akatsuki.immunizationsystem.model.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TNazivVakcineIzvrseneVakcinacije", namespace = "http://www.akatsuki.org/potvrda_o_izvrsenoj_vakcinaciji")
@XmlEnum
public enum TNazivVakcineIzvrseneVakcinacije {

    @XmlEnumValue("Pfizer-BioNTech")
    PFIZER_BIO_N_TECH("Pfizer-BioNTech"),
    @XmlEnumValue("Sputnik V")
    SPUTNIK_V("Sputnik V"),
    @XmlEnumValue("Sinopharm")
    SINOPHARM("Sinopharm"),
    @XmlEnumValue("AstraZeneca")
    ASTRA_ZENECA("AstraZeneca"),
    @XmlEnumValue("Moderna")
    MODERNA("Moderna");
    private final String value;

    TNazivVakcineIzvrseneVakcinacije(String v) {
        value = v;
    }

    public static TNazivVakcineIzvrseneVakcinacije fromValue(String v) {
        for (TNazivVakcineIzvrseneVakcinacije c : TNazivVakcineIzvrseneVakcinacije.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
