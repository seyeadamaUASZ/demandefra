
package sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statutEnrolement" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "statutEnrolement"
})
@XmlRootElement(name = "EnroleeyoneResponse")
public class EnroleeyoneResponse {

    @XmlElement(required = true)
    protected String statutEnrolement;

    /**
     * Obtient la valeur de la propriété statutEnrolement.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutEnrolement() {
        return statutEnrolement;
    }

    /**
     * Définit la valeur de la propriété statutEnrolement.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutEnrolement(String value) {
        this.statutEnrolement = value;
    }

}
