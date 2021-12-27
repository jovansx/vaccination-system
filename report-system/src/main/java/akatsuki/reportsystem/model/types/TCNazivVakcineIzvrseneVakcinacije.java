package akatsuki.reportsystem.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TCNazivVakcineIzvrseneVakcinacije", propOrder = {
        "value"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TCNazivVakcineIzvrseneVakcinacije {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "property", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String property;
    @XmlAttribute(name = "datatype", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String datatype;
}
