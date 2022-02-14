package akatsuki.officialsystem.model.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vaccine", propOrder = {
        "type",
        "sideEffect",
        "manufacturer",
        "series",
})
@XmlRootElement(name = "vaccine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {
    @XmlElement(required = true)
    private VaccineType type;
    @XmlElement(required = true)
    private String sideEffect;
    @XmlElement(required = true)
    private String manufacturer;
    @XmlElement(required = true)
    protected List<Serie> series;


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "amount",
            "serialNumber"
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Serie {
        @XmlElement(required = true)
        private Long amount;
        @XmlElement(required = true)
        private Long serialNumber;
    }

}