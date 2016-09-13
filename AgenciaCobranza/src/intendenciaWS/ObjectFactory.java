
package intendenciaWS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the intendenciaWS package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ParkingCancelResponse_QNAME = new QName("http://services/", "parkingCancelResponse");
    private final static QName _ParkingSaleResponse_QNAME = new QName("http://services/", "parkingSaleResponse");
    private final static QName _ParkingCancel_QNAME = new QName("http://services/", "parkingCancel");
    private final static QName _ParkingSale_QNAME = new QName("http://services/", "parkingSale");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: intendenciaWS
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ParkingSale }
     * 
     */
    public ParkingSale createParkingSale() {
        return new ParkingSale();
    }

    /**
     * Create an instance of {@link ParkingCancel }
     * 
     */
    public ParkingCancel createParkingCancel() {
        return new ParkingCancel();
    }

    /**
     * Create an instance of {@link ParkingCancelResponse }
     * 
     */
    public ParkingCancelResponse createParkingCancelResponse() {
        return new ParkingCancelResponse();
    }

    /**
     * Create an instance of {@link ParkingSaleResponse }
     * 
     */
    public ParkingSaleResponse createParkingSaleResponse() {
        return new ParkingSaleResponse();
    }

    /**
     * Create an instance of {@link Sale }
     * 
     */
    public Sale createSale() {
        return new Sale();
    }

    /**
     * Create an instance of {@link Credit }
     * 
     */
    public Credit createCredit() {
        return new Credit();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParkingCancelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "parkingCancelResponse")
    public JAXBElement<ParkingCancelResponse> createParkingCancelResponse(ParkingCancelResponse value) {
        return new JAXBElement<ParkingCancelResponse>(_ParkingCancelResponse_QNAME, ParkingCancelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParkingSaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "parkingSaleResponse")
    public JAXBElement<ParkingSaleResponse> createParkingSaleResponse(ParkingSaleResponse value) {
        return new JAXBElement<ParkingSaleResponse>(_ParkingSaleResponse_QNAME, ParkingSaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParkingCancel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "parkingCancel")
    public JAXBElement<ParkingCancel> createParkingCancel(ParkingCancel value) {
        return new JAXBElement<ParkingCancel>(_ParkingCancel_QNAME, ParkingCancel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParkingSale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "parkingSale")
    public JAXBElement<ParkingSale> createParkingSale(ParkingSale value) {
        return new JAXBElement<ParkingSale>(_ParkingSale_QNAME, ParkingSale.class, null, value);
    }

}
