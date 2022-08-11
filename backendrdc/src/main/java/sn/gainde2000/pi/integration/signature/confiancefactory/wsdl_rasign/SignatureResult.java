
package sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour SignatureResult complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="SignatureResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErroInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureResult", propOrder = {
    "erroInfo",
    "status",
    "content"
})
public class SignatureResult {

    @XmlElement(name = "ErroInfo", required = true)
    protected String erroInfo;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "Content", required = true)
    protected byte[] content;

    /**
     * Obtient la valeur de la propriété erroInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErroInfo() {
        return erroInfo;
    }

    /**
     * Définit la valeur de la propriété erroInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErroInfo(String value) {
        this.erroInfo = value;
    }

    /**
     * Obtient la valeur de la propriété status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit la valeur de la propriété status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Obtient la valeur de la propriété content.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Définit la valeur de la propriété content.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContent(byte[] value) {
        this.content = value;
    }

}
