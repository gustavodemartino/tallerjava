package intendencia;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.14.redhat-1
 * 2016-09-12T10:50:55.424-03:00
 * Generated source version: 2.7.14.redhat-1
 * 
 */
@WebService(targetNamespace = "http://services/", name = "Parking")
@XmlSeeAlso({ObjectFactory.class})
public interface Parking {

    @WebMethod
    @RequestWrapper(localName = "parkingCancel", targetNamespace = "http://services/", className = "intendencia.ParkingCancel")
    @ResponseWrapper(localName = "parkingCancelResponse", targetNamespace = "http://services/", className = "intendencia.ParkingCancelResponse")
    @WebResult(name = "return", targetNamespace = "")
    public intendencia.Credit parkingCancel(
        @WebParam(name = "angencyId", targetNamespace = "")
        java.lang.String angencyId,
        @WebParam(name = "eTicketNumber", targetNamespace = "")
        long eTicketNumber
    );

    @WebMethod
    @RequestWrapper(localName = "parkingSale", targetNamespace = "http://services/", className = "intendencia.ParkingSale")
    @ResponseWrapper(localName = "parkingSaleResponse", targetNamespace = "http://services/", className = "intendencia.ParkingSaleResponse")
    @WebResult(name = "return", targetNamespace = "")
    public intendencia.Sale parkingSale(
        @WebParam(name = "angencyId", targetNamespace = "")
        java.lang.String angencyId,
        @WebParam(name = "plate", targetNamespace = "")
        java.lang.String plate,
        @WebParam(name = "startTime", targetNamespace = "")
        long startTime,
        @WebParam(name = "minutes", targetNamespace = "")
        int minutes
    );
}