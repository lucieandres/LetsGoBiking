
package com.soap.ws.client.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ArrayOfGeoPosition complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGeoPosition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GeoPosition" type="{http://schemas.datacontract.org/2004/07/StackExchange.Redis}GeoPosition" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGeoPosition", namespace = "http://schemas.datacontract.org/2004/07/StackExchange.Redis", propOrder = {
    "geoPosition"
})
public class ArrayOfGeoPosition {

    @XmlElement(name = "GeoPosition")
    protected List<GeoPosition> geoPosition;

    /**
     * Gets the value of the geoPosition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoPosition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGeoPosition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoPosition }
     * 
     * 
     */
    public List<GeoPosition> getGeoPosition() {
        if (geoPosition == null) {
            geoPosition = new ArrayList<GeoPosition>();
        }
        return this.geoPosition;
    }

}
