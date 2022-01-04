package akatsuki.immunizationsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "dokumenti")
public class PacijentDokumentiDTO {
    List<DokumentIdDTO> dokumenti;
}
