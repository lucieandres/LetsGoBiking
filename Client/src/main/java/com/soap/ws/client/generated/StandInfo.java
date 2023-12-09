
package com.soap.ws.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour StandInfo complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="StandInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="availabilities" type="{http://schemas.datacontract.org/2004/07/RoutingServer}Availabilities" minOccurs="0"/&gt;
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StandInfo", namespace = "http://schemas.datacontract.org/2004/07/RoutingServer", propOrder = {
    "availabilities",
    "capacity"
})
public class StandInfo {

    @XmlElementRef(name = "availabilities", namespace = "http://schemas.datacontract.org/2004/07/RoutingServer", type = JAXBElement.class, required = false)
    protected JAXBElement<Availabilities> availabilities;
    protected Integer capacity;

    /**
     * Obtient la valeur de la propriété availabilities.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Availabilities }{@code >}
     *     
     */
    public JAXBElement<Availabilities> getAvailabilities() {
        return availabilities;
    }

    /**
     * Définit la valeur de la propriété availabilities.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Availabilities }{@code >}
     *     
     */
    public void setAvailabilities(JAXBElement<Availabilities> value) {
        this.availabilities = value;
    }

    /**
     * Obtient la valeur de la propriété capacity.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Définit la valeur de la propriété capacity.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapacity(Integer value) {
        this.capacity = value;
    }

}
