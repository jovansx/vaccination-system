//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.14 at 09:29:21 AM CET 
//


package akatsuki.immunizationsystem.model.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPodnosilacInteresovanja complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodnosilacInteresovanja"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.akatsuki.org/tipovi}TKorisnik"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="email" type="{http://www.akatsuki.org/tipovi}TEmail"/&gt;
 *         &lt;element name="fiksni_telefon" type="{http://www.akatsuki.org/tipovi}TTelefon"/&gt;
 *         &lt;element name="mobilni_telefon" type="{http://www.akatsuki.org/tipovi}TTelefon"/&gt;
 *         &lt;element name="lokacija" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodnosilacInteresovanja", propOrder = {
    "email",
    "fiksniTelefon",
    "mobilniTelefon",
    "lokacija"
})
public class TPodnosilacInteresovanja
    extends TKorisnik
{

    @XmlElement(required = true)
    protected String email;
    @XmlElement(name = "fiksni_telefon", required = true)
    protected String fiksniTelefon;
    @XmlElement(name = "mobilni_telefon", required = true)
    protected String mobilniTelefon;
    @XmlElement(required = true)
    protected String lokacija;

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the fiksniTelefon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiksniTelefon() {
        return fiksniTelefon;
    }

    /**
     * Sets the value of the fiksniTelefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiksniTelefon(String value) {
        this.fiksniTelefon = value;
    }

    /**
     * Gets the value of the mobilniTelefon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilniTelefon() {
        return mobilniTelefon;
    }

    /**
     * Sets the value of the mobilniTelefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilniTelefon(String value) {
        this.mobilniTelefon = value;
    }

    /**
     * Gets the value of the lokacija property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLokacija() {
        return lokacija;
    }

    /**
     * Sets the value of the lokacija property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLokacija(String value) {
        this.lokacija = value;
    }

}
