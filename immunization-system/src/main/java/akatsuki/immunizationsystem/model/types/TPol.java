package akatsuki.immunizationsystem.model.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TPol")
@XmlEnum
public enum TPol {

    @XmlEnumValue("Muski")
    MUSKI("Muski"),
    @XmlEnumValue("Zenski")
    ZENSKI("Zenski");
    private final String value;

    TPol(String v) {
        value = v;
    }

    public static TPol fromValue(String v) {
        for (TPol c : TPol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
