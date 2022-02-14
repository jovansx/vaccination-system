package akatsuki.officialsystem.model.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinesDTO", propOrder = {
        "vaccines"
})
@XmlRootElement(name = "vaccinesDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinesDTO {
    @XmlElement(required = true)
    protected List<VaccineDTO> vaccines;
}
