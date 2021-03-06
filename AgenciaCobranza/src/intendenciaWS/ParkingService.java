package intendenciaWS;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.14.redhat-1
 * 2016-10-03T13:19:13.364-03:00
 * Generated source version: 2.7.14.redhat-1
 * 
 */
@WebServiceClient(name = "ParkingService", 
                  wsdlLocation = "http://localhost:8080/Intendencia/Parking?wsdl",
                  targetNamespace = "http://services/") 
public class ParkingService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://services/", "ParkingService");
    public final static QName ParkingPort = new QName("http://services/", "ParkingPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/Intendencia/Parking?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ParkingService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/Intendencia/Parking?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ParkingService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ParkingService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ParkingService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns Parking
     */
    @WebEndpoint(name = "ParkingPort")
    public Parking getParkingPort() {
        return super.getPort(ParkingPort, Parking.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Parking
     */
    @WebEndpoint(name = "ParkingPort")
    public Parking getParkingPort(WebServiceFeature... features) {
        return super.getPort(ParkingPort, Parking.class, features);
    }

}
