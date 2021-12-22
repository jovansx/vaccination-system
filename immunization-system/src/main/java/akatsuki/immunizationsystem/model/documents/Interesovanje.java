package akatsuki.immunizationsystem.model.documents;

import akatsuki.immunizationsystem.model.types.TDrzavljanstvo;
import akatsuki.immunizationsystem.model.types.TPodnosilacInteresovanja;
import akatsuki.immunizationsystem.model.types.TTipVakcine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "drzavljanstvo",
        "podnosilac",
        "vakcine",
        "dobrovoljniDavalacKrvi"
})
@XmlRootElement(name = "interesovanje", namespace = "http://www.akatsuki.org/interesovanje")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interesovanje {

    @XmlElement(namespace = "http://www.akatsuki.org/interesovanje", required = true)
    @XmlSchemaType(name = "string")
    protected TDrzavljanstvo drzavljanstvo;
    @XmlElement(namespace = "http://www.akatsuki.org/interesovanje", required = true)
    protected TPodnosilacInteresovanja podnosilac;
    @XmlElement(namespace = "http://www.akatsuki.org/interesovanje", required = true)
    protected Vakcine vakcine;
    @XmlElement(name = "dobrovoljni_davalac_krvi", namespace = "http://www.akatsuki.org/interesovanje")
    protected boolean dobrovoljniDavalacKrvi;
    @XmlAttribute(name = "about", required = true)
    protected String about;
    @XmlAttribute(name = "rel")
    protected String rel;
    @XmlAttribute(name = "href")
    protected String href;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"vakcina"})
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Vakcine {

        @XmlElement(namespace = "http://www.akatsuki.org/interesovanje", required = true)
        protected List<TTipVakcine> vakcina;
    }
}
