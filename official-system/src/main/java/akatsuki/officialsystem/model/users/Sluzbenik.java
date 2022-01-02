package akatsuki.officialsystem.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sluzbenik", propOrder = {
        "ime",
        "prezime",
        "idBroj",
        "fiksniTelefon",
        "email",
        "sifra"
})
@XmlRootElement(name = "sluzbenik")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sluzbenik {
    @XmlElement(required = true, name = "id_broj")
    private String idBroj;
    @XmlElement(required = true)
    private String ime;
    @XmlElement(required = true)
    private String prezime;
    @XmlElement(required = true, name = "fiksni_telefon")
    private String fiksniTelefon;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String sifra;
}
