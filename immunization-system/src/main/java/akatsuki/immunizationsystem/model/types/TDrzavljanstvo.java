package akatsuki.immunizationsystem.model.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TDrzavljanstvo", namespace = "http://www.akatsuki.org/interesovanje")
@XmlEnum
public enum TDrzavljanstvo {

    @XmlEnumValue("srpsko")
    SRPSKO("srpsko"),
    @XmlEnumValue("strano sa boravkom")
    STRANO_SA_BORAVKOM("strano sa boravkom"),
    @XmlEnumValue("strano bez boravka")
    STRANO_BEZ_BORAVKA("strano bez boravka");
    private final String value;

    TDrzavljanstvo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TDrzavljanstvo fromValue(String v) {
        for (TDrzavljanstvo c: TDrzavljanstvo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
