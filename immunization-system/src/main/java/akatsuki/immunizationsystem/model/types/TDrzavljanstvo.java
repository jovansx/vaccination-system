//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.14 at 09:29:21 AM CET 
//


package akatsuki.immunizationsystem.model.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TDrzavljanstvo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TDrzavljanstvo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="srpsko"/&gt;
 *     &lt;enumeration value="strano sa boravkom"/&gt;
 *     &lt;enumeration value="strano bez boravka"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
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