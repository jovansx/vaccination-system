package akatsuki.officialsystem.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPunoIme", propOrder = {
        "prezime",
        "ime",
        "imeRoditelja"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TPunoIme {
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected TPrezime prezime;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected TIme ime;
    @XmlElement(required = true, name = "ime_roditelja")
    protected String imeRoditelja;
}
