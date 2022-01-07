package akatsuki.officialsystem.model.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RaspodelaPoProizvodjacimaDTO", propOrder = {
        "pfizerBioNTech",
        "sputnikV",
        "sinopharm",
        "astraZeneca",
        "moderna"
})
@XmlRootElement(name = "raspodelaPoProizvodjacimaDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaspodelaPoProizvodjacimaDTO {

    @XmlElement(required = true, name = "pfizerBioNTech")
    protected Integer pfizerBioNTech;
    @XmlElement(required = true, name = "sputnikV")
    protected Integer sputnikV;
    @XmlElement(required = true, name = "sinopharm")
    protected Integer sinopharm;
    @XmlElement(required = true, name = "astraZeneca")
    protected Integer astraZeneca;
    @XmlElement(required = true, name = "moderna")
    protected Integer moderna;
}
