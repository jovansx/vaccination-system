package akatsuki.officialsystem.model.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TNazivVakcine")
@XmlEnum
public enum TNazivVakcine {

    @XmlEnumValue("Pfizer-BioNTech")
    PFIZER_BIO_N_TECH("Pfizer-BioNTech"),
    @XmlEnumValue("Sputnik V (Gamaleya istrazivacki centar)")
    SPUTNIK_V_GAMALEYA_ISTRAZIVACKI_CENTAR("Sputnik V (Gamaleya istrazivacki centar)"),
    @XmlEnumValue("Sinopharm")
    SINOPHARM("Sinopharm"),
    @XmlEnumValue("AstraZeneca")
    ASTRA_ZENECA("AstraZeneca"),
    @XmlEnumValue("Moderna")
    MODERNA("Moderna"),
    @XmlEnumValue("Bilo koja")
    BILO_KOJA("Bilo koja");
    private final String value;

    TNazivVakcine(String v) {
        value = v;
    }

    public static TNazivVakcine fromValue(String v) {
        for (TNazivVakcine c : TNazivVakcine.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
