//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.14 at 09:29:21 AM CET 
//


package akatsuki.immunizationsystem.model.types;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for TKorisnik complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TKorisnik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="prezime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="jmbg" type="{http://www.akatsuki.org/tipovi}TJmbg"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TKorisnik", propOrder = {
        "ime",
        "prezime",
        "jmbg"
})
@XmlSeeAlso({
        TPodnosilacZahteva.class,
        TPodnosilacInteresovanja.class,
        TPrimalac.class
})
public abstract class TKorisnik {

    @XmlElement(required = true)
    protected String ime;
    @XmlElement(required = true)
    protected String prezime;
    @XmlElement(required = true)
    protected String jmbg;

    /**
     * Gets the value of the ime property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIme() {
        return ime;
    }

    /**
     * Sets the value of the ime property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIme(String value) {
        this.ime = value;
    }

    /**
     * Gets the value of the prezime property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Sets the value of the prezime property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrezime(String value) {
        this.prezime = value;
    }

    /**
     * Gets the value of the jmbg property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getJmbg() {
        return jmbg;
    }

    /**
     * Sets the value of the jmbg property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setJmbg(String value) {
        this.jmbg = value;
    }

}
