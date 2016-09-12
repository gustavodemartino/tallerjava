
package intendencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for credit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="credit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="saleDate" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="eCreditNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="eTicketNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "credit", propOrder = {
    "amount",
    "customerName",
    "message",
    "result",
    "saleDate",
    "eCreditNumber",
    "eTicketNumber"
})
public class Credit {

    protected long amount;
    protected String customerName;
    protected String message;
    protected int result;
    protected long saleDate;
    protected long eCreditNumber;
    protected long eTicketNumber;

    /**
     * Gets the value of the amount property.
     * 
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(long value) {
        this.amount = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the result property.
     * 
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     */
    public void setResult(int value) {
        this.result = value;
    }

    /**
     * Gets the value of the saleDate property.
     * 
     */
    public long getSaleDate() {
        return saleDate;
    }

    /**
     * Sets the value of the saleDate property.
     * 
     */
    public void setSaleDate(long value) {
        this.saleDate = value;
    }

    /**
     * Gets the value of the eCreditNumber property.
     * 
     */
    public long getECreditNumber() {
        return eCreditNumber;
    }

    /**
     * Sets the value of the eCreditNumber property.
     * 
     */
    public void setECreditNumber(long value) {
        this.eCreditNumber = value;
    }

    /**
     * Gets the value of the eTicketNumber property.
     * 
     */
    public long getETicketNumber() {
        return eTicketNumber;
    }

    /**
     * Sets the value of the eTicketNumber property.
     * 
     */
    public void setETicketNumber(long value) {
        this.eTicketNumber = value;
    }

}
