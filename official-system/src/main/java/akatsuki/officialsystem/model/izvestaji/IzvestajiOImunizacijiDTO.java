package akatsuki.officialsystem.model.izvestaji;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IzvestajiOImunizacijiDTO", propOrder = {
        "izvestajPeriod"
})
@XmlRootElement(name = "izvestajiOImunizacijiDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IzvestajiOImunizacijiDTO {
    @XmlElement(required = true)
    protected List<IzvestajOImunizacijiPeriodDTO> izvestajPeriod;
}
