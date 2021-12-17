package akatsuki.immunizationsystem.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodnosilacInteresovanja", propOrder = {
        "email",
        "fiksniTelefon",
        "mobilniTelefon",
        "lokacija"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TPodnosilacInteresovanja
        extends TKorisnik {

    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected String email;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", name = "fiksni_telefon", required = true)
    protected String fiksniTelefon;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", name = "mobilni_telefon", required = true)
    protected String mobilniTelefon;
    @XmlElement(namespace = "http://www.akatsuki.org/tipovi", required = true)
    protected String lokacija;
}
