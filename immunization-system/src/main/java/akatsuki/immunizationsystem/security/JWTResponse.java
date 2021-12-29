package akatsuki.immunizationsystem.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "jwt_response")
public class JWTResponse implements Serializable {
    private String jwtToken;
}

