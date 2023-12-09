
package com.soap.ws.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour GeoCoordinate complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="GeoCoordinate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Altitude" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="Course" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="HorizontalAccuracy" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="Speed" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="VerticalAccuracy" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeoCoordinate", namespace = "http://schemas.datacontract.org/2004/07/System.Device.Location", propOrder = {
    "altitude",
    "course",
    "horizontalAccuracy",
    "latitude",
    "longitude",
    "speed",
    "verticalAccuracy"
})
public class GeoCoordinate {

    @XmlElement(name = "Altitude")
    protected Double altitude;
    @XmlElement(name = "Course")
    protected Double course;
    @XmlElement(name = "HorizontalAccuracy")
    protected Double horizontalAccuracy;
    @XmlElement(name = "Latitude")
    protected Double latitude;
    @XmlElement(name = "Longitude")
    protected Double longitude;
    @XmlElement(name = "Speed")
    protected Double speed;
    @XmlElement(name = "VerticalAccuracy")
    protected Double verticalAccuracy;

    /**
     * Obtient la valeur de la propriété altitude.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAltitude() {
        return altitude;
    }

    /**
     * Définit la valeur de la propriété altitude.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAltitude(Double value) {
        this.altitude = value;
    }

    /**
     * Obtient la valeur de la propriété course.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCourse() {
        return course;
    }

    /**
     * Définit la valeur de la propriété course.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCourse(Double value) {
        this.course = value;
    }

    /**
     * Obtient la valeur de la propriété horizontalAccuracy.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHorizontalAccuracy() {
        return horizontalAccuracy;
    }

    /**
     * Définit la valeur de la propriété horizontalAccuracy.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHorizontalAccuracy(Double value) {
        this.horizontalAccuracy = value;
    }

    /**
     * Obtient la valeur de la propriété latitude.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Définit la valeur de la propriété latitude.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLatitude(Double value) {
        this.latitude = value;
    }

    /**
     * Obtient la valeur de la propriété longitude.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Définit la valeur de la propriété longitude.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLongitude(Double value) {
        this.longitude = value;
    }

    /**
     * Obtient la valeur de la propriété speed.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * Définit la valeur de la propriété speed.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSpeed(Double value) {
        this.speed = value;
    }

    /**
     * Obtient la valeur de la propriété verticalAccuracy.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVerticalAccuracy() {
        return verticalAccuracy;
    }

    /**
     * Définit la valeur de la propriété verticalAccuracy.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVerticalAccuracy(Double value) {
        this.verticalAccuracy = value;
    }

}
