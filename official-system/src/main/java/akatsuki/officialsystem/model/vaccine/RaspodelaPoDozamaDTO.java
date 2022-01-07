package akatsuki.officialsystem.model.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RaspodelaPoDozamaDTO", propOrder = {
        "doza1",
        "doza2"
})
@XmlRootElement(name = "raspodelaPoDozamaDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaspodelaPoDozamaDTO {

    @XmlElement(required = true, name = "doza1")
    protected Integer doza1;
    @XmlElement(required = true, name = "doza2")
    protected Integer doza2;
}
