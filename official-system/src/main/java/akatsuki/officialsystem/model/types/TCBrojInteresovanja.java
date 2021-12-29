package akatsuki.officialsystem.model.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TCBrojInteresovanja", namespace = "http://www.akatsuki.org/izvestaj_o_imunizaciji", propOrder = {
    "value"
})
public class TCBrojInteresovanja {

    @XmlValue
    protected int value;
    @XmlAttribute(name = "property", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String property;
    @XmlAttribute(name = "datatype", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String datatype;
}
