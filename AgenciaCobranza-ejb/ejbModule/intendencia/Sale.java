
package intendencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minutes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="plate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="saleDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="startDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
@XmlType(name = "sale", propOrder = {
    "amount",
    "customerName",
    "minutes",
    "plate",
    "saleDate",
    "startDateTime",
    "eTicketNumber"
})
public class Sale {

    protected long amount;
    protected String customerName;
    protected int minutes;
    protected String plate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar saleDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDateTime;
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
     * Gets the value of the minutes property.
     * 
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Sets the value of the minutes property.
     * 
     */
    public void setMinutes(int value) {
        this.minutes = value;
    }

    /**
     * Gets the value of the plate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the value of the plate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlate(String value) {
        this.plate = value;
    }

    /**
     * Gets the value of the saleDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSaleDate() {
        return saleDate;
    }

    /**
     * Sets the value of the saleDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSaleDate(XMLGregorianCalendar value) {
        this.saleDate = value;
    }

    /**
     * Gets the value of the startDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets the value of the startDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDateTime(XMLGregorianCalendar value) {
        this.startDateTime = value;
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
