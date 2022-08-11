
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
 *         &lt;element name="IdentificationUtilisateur" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "identificationUtilisateur"
})
@XmlRootElement(name = "EnregistrerBeneficiaireResponse")
public class EnregistrerBeneficiaireResponse {

    @XmlElement(name = "IdentificationUtilisateur", required = true)
    protected String identificationUtilisateur;

    /**
     * Obtient la valeur de la propriété identificationUtilisateur.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationUtilisateur() {
        return identificationUtilisateur;
    }

    /**
     * Définit la valeur de la propriété identificationUtilisateur.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificationUtilisateur(String value) {
        this.identificationUtilisateur = value;
    }

}
