package akatsuki.officialsystem.model.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccineDTO", propOrder = {
        "type",
        "name",
        "sideEffect",
        "manufacturer",
        "series",
        "amount"
})
@XmlRootElement(name = "vaccineDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDTO {
    @XmlElement(required = true)
    private VaccineType type;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String sideEffect;
    @XmlElement(required = true)
    private String manufacturer;
    @XmlElement(required = true)
    protected Long series;
    @XmlElement(required = true)
    private Long amount;
}
