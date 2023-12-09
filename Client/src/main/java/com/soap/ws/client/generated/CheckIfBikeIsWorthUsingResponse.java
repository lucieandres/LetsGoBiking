
package com.soap.ws.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CheckIfBikeIsWorthUsingResult" type="{http://schemas.datacontract.org/2004/07/System.Device.Location}ArrayOfGeoCoordinate" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "checkIfBikeIsWorthUsingResult"
})
@XmlRootElement(name = "CheckIfBikeIsWorthUsingResponse")
public class CheckIfBikeIsWorthUsingResponse {

    @XmlElementRef(name = "CheckIfBikeIsWorthUsingResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfGeoCoordinate> checkIfBikeIsWorthUsingResult;

    /**
     * Obtient la valeur de la propriété checkIfBikeIsWorthUsingResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfGeoCoordinate }{@code >}
     *     
     */
    public JAXBElement<ArrayOfGeoCoordinate> getCheckIfBikeIsWorthUsingResult() {
        return checkIfBikeIsWorthUsingResult;
    }

    /**
     * Définit la valeur de la propriété checkIfBikeIsWorthUsingResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfGeoCoordinate }{@code >}
     *     
     */
    public void setCheckIfBikeIsWorthUsingResult(JAXBElement<ArrayOfGeoCoordinate> value) {
        this.checkIfBikeIsWorthUsingResult = value;
    }

}
