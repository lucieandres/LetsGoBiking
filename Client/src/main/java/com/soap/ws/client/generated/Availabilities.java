
package com.soap.ws.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Availabilities complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Availabilities"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bikes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="electricalBikes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="electricalInternalBatteryBikes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="electricalRemovableBatteryBikes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="mechanicalBikes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="stands" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Availabilities", namespace = "http://schemas.datacontract.org/2004/07/RoutingServer", propOrder = {
    "bikes",
    "electricalBikes",
    "electricalInternalBatteryBikes",
    "electricalRemovableBatteryBikes",
    "mechanicalBikes",
    "stands"
})
public class Availabilities {

    protected Integer bikes;
    protected Integer electricalBikes;
    protected Integer electricalInternalBatteryBikes;
    protected Integer electricalRemovableBatteryBikes;
    protected Integer mechanicalBikes;
    protected Integer stands;

    /**
     * Obtient la valeur de la propriété bikes.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBikes() {
        return bikes;
    }

    /**
     * Définit la valeur de la propriété bikes.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBikes(Integer value) {
        this.bikes = value;
    }

    /**
     * Obtient la valeur de la propriété electricalBikes.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getElectricalBikes() {
        return electricalBikes;
    }

    /**
     * Définit la valeur de la propriété electricalBikes.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setElectricalBikes(Integer value) {
        this.electricalBikes = value;
    }

    /**
     * Obtient la valeur de la propriété electricalInternalBatteryBikes.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getElectricalInternalBatteryBikes() {
        return electricalInternalBatteryBikes;
    }

    /**
     * Définit la valeur de la propriété electricalInternalBatteryBikes.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setElectricalInternalBatteryBikes(Integer value) {
        this.electricalInternalBatteryBikes = value;
    }

    /**
     * Obtient la valeur de la propriété electricalRemovableBatteryBikes.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getElectricalRemovableBatteryBikes() {
        return electricalRemovableBatteryBikes;
    }

    /**
     * Définit la valeur de la propriété electricalRemovableBatteryBikes.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setElectricalRemovableBatteryBikes(Integer value) {
        this.electricalRemovableBatteryBikes = value;
    }

    /**
     * Obtient la valeur de la propriété mechanicalBikes.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMechanicalBikes() {
        return mechanicalBikes;
    }

    /**
     * Définit la valeur de la propriété mechanicalBikes.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMechanicalBikes(Integer value) {
        this.mechanicalBikes = value;
    }

    /**
     * Obtient la valeur de la propriété stands.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStands() {
        return stands;
    }

    /**
     * Définit la valeur de la propriété stands.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStands(Integer value) {
        this.stands = value;
    }

}
