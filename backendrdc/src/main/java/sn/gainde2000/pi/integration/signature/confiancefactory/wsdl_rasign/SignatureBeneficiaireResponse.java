
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
 *         &lt;element name="sortie" type="{http://www.confiancefactory.com/WSDL_RASign/}SignatureResult"/>
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
    "sortie"
})
@XmlRootElement(name = "SignatureBeneficiaireResponse")
public class SignatureBeneficiaireResponse {

    @XmlElement(required = true)
    protected SignatureResult sortie;

    /**
     * Obtient la valeur de la propriété sortie.
     * 
     * @return
     *     possible object is
     *     {@link SignatureResult }
     *     
     */
    public SignatureResult getSortie() {
        return sortie;
    }

    /**
     * Définit la valeur de la propriété sortie.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureResult }
     *     
     */
    public void setSortie(SignatureResult value) {
        this.sortie = value;
    }

}
