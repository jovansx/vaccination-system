package akatsuki.immunizationsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "dokument_id")
public class DokumentIdDTO {
    private String nazivDokumenta;
    private String idDokumenta;
}
