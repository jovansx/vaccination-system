package akatsuki.officialsystem.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TTipVakcine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TTipVakcine {

    @XmlAttribute(name = "nazivVakcine", required = true)
    protected TNazivVakcine nazivVakcine;
}
