
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
 *         &lt;element name="user_id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="document_a_signer" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
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
    "userId",
    "documentASigner"
})
@XmlRootElement(name = "SignatureBeneficiaire")
public class SignatureBeneficiaire {

    @XmlElement(name = "user_id")
    protected long userId;
    @XmlElement(name = "document_a_signer", required = true)
    protected byte[] documentASigner;

    /**
     * Obtient la valeur de la propriété userId.
     * 
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Définit la valeur de la propriété userId.
     * 
     */
    public void setUserId(long value) {
        this.userId = value;
    }

    /**
     * Obtient la valeur de la propriété documentASigner.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumentASigner() {
        return documentASigner;
    }

    /**
     * Définit la valeur de la propriété documentASigner.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumentASigner(byte[] value) {
        this.documentASigner = value;
    }

}
