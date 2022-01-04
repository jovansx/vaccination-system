package akatsuki.officialsystem.model.izvestaji;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IzvestajOImunizacijiPeriodDTO", propOrder = {
        "id",
        "period"
})
@XmlRootElement(name = "izvestajOImunizacijiPeriodDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IzvestajOImunizacijiPeriodDTO {
    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String period;
}
