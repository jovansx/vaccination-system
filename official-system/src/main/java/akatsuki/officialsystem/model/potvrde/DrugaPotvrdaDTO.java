package akatsuki.officialsystem.model.potvrde;

import lombok.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugaPotvrdaDTO", propOrder = {
        "idBroj",
        "doza1Datum",
        "doza1Serija",
        "doza2Datum",
        "doza2Serija",
        "zdravstvenaUstanova",
        "nazivVakcine"
})
@XmlRootElement(name = "drugaPotvrdaDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugaPotvrdaDTO {
    @XmlElement(required = true)
    private String idBroj;
    @XmlElement(required = true)
    private String doza1Datum;
    @XmlElement(required = true)
    private String doza1Serija;
    @XmlElement(required = true)
    private String doza2Datum;
    @XmlElement(required = true)
    private String doza2Serija;
    @XmlElement(required = true)
    private String zdravstvenaUstanova;
    @XmlElement(required = true)
    private String nazivVakcine;
}
