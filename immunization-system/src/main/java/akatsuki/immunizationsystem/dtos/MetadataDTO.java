package akatsuki.immunizationsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDTO {
    private String subject;
    private HashMap<String, String> values = new HashMap<>();
}
