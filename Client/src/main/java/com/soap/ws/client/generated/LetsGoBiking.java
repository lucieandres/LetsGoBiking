
package com.soap.ws.client.generated;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "LetsGoBiking", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://localhost:8733/MyService/LetsGoBiking?wsdl")
public class LetsGoBiking
    extends Service
{

    private final static URL LETSGOBIKING_WSDL_LOCATION;
    private final static WebServiceException LETSGOBIKING_EXCEPTION;
    private final static QName LETSGOBIKING_QNAME = new QName("http://tempuri.org/", "LetsGoBiking");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8733/MyService/LetsGoBiking?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        LETSGOBIKING_WSDL_LOCATION = url;
        LETSGOBIKING_EXCEPTION = e;
    }

    public LetsGoBiking() {
        super(__getWsdlLocation(), LETSGOBIKING_QNAME);
    }

    public LetsGoBiking(WebServiceFeature... features) {
        super(__getWsdlLocation(), LETSGOBIKING_QNAME, features);
    }

    public LetsGoBiking(URL wsdlLocation) {
        super(wsdlLocation, LETSGOBIKING_QNAME);
    }

    public LetsGoBiking(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, LETSGOBIKING_QNAME, features);
    }

    public LetsGoBiking(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LetsGoBiking(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ILetsGoBiking
     */
    @WebEndpoint(name = "BasicHttpBinding_ILetsGoBiking")
    public ILetsGoBiking getBasicHttpBindingILetsGoBiking() {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_ILetsGoBiking"), ILetsGoBiking.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ILetsGoBiking
     */
    @WebEndpoint(name = "BasicHttpBinding_ILetsGoBiking")
    public ILetsGoBiking getBasicHttpBindingILetsGoBiking(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_ILetsGoBiking"), ILetsGoBiking.class, features);
    }

    private static URL __getWsdlLocation() {
        if (LETSGOBIKING_EXCEPTION!= null) {
            throw LETSGOBIKING_EXCEPTION;
        }
        return LETSGOBIKING_WSDL_LOCATION;
    }

}
