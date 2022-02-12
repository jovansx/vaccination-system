package akatsuki.officialsystem.model.zahtevi;

import akatsuki.officialsystem.model.documents.ZahtevZaSertifikat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NeodobreniZahteviDTO", propOrder = {
        "neodobrenZahtevDTOList"
})
@XmlRootElement(name = "neodobreniZahteviDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeodobreniZahteviDTO {
    @XmlElement(required = true)
    protected List<NeodobrenZahtevDTO> neodobrenZahtevDTOList;
}
