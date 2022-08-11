
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
 *         &lt;element name="prenom_nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numero_cni" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code_pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="scan_cni" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="IdAppAppelant" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "prenomNom",
    "numeroCni",
    "codePin",
    "scanCni",
    "idAppAppelant"
})
@XmlRootElement(name = "Enroleeyone")
public class Enroleeyone {

    @XmlElement(name = "prenom_nom", required = true)
    protected String prenomNom;
    @XmlElement(name = "numero_cni", required = true)
    protected String numeroCni;
    @XmlElement(name = "code_pin", required = true)
    protected String codePin;
    @XmlElement(name = "scan_cni", required = true)
    protected byte[] scanCni;
    @XmlElement(name = "IdAppAppelant")
    protected long idAppAppelant;

    /**
     * Obtient la valeur de la propriété prenomNom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenomNom() {
        return prenomNom;
    }

    /**
     * Définit la valeur de la propriété prenomNom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenomNom(String value) {
        this.prenomNom = value;
    }

    /**
     * Obtient la valeur de la propriété numeroCni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCni() {
        return numeroCni;
    }

    /**
     * Définit la valeur de la propriété numeroCni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCni(String value) {
        this.numeroCni = value;
    }

    /**
     * Obtient la valeur de la propriété codePin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodePin() {
        return codePin;
    }

    /**
     * Définit la valeur de la propriété codePin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodePin(String value) {
        this.codePin = value;
    }

    /**
     * Obtient la valeur de la propriété scanCni.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getScanCni() {
        return scanCni;
    }

    /**
     * Définit la valeur de la propriété scanCni.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setScanCni(byte[] value) {
        this.scanCni = value;
    }

    /**
     * Obtient la valeur de la propriété idAppAppelant.
     * 
     */
    public long getIdAppAppelant() {
        return idAppAppelant;
    }

    /**
     * Définit la valeur de la propriété idAppAppelant.
     * 
     */
    public void setIdAppAppelant(long value) {
        this.idAppAppelant = value;
    }

}
