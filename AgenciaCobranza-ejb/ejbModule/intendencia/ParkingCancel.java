
package intendencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parkingCancel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parkingCancel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="angencyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "parkingCancel", propOrder = {
    "angencyId",
    "eTicketNumber"
})
public class ParkingCancel {

    protected String angencyId;
    protected long eTicketNumber;

    /**
     * Gets the value of the angencyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAngencyId() {
        return angencyId;
    }

    /**
     * Sets the value of the angencyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAngencyId(String value) {
        this.angencyId = value;
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
