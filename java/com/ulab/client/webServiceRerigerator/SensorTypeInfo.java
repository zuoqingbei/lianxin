
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SensorTypeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SensorTypeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="highValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="lowValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="sensorTypeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sensorTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testUnitId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SensorTypeInfo", namespace = "http://bean", propOrder = {
    "highValue",
    "lowValue",
    "sensorTypeId",
    "sensorTypeName",
    "testUnitId",
    "unit"
})
public class SensorTypeInfo {

    protected Float highValue;
    protected Float lowValue;
    protected Integer sensorTypeId;
    @XmlElement(nillable = true)
    protected String sensorTypeName;
    protected Integer testUnitId;
    @XmlElement(nillable = true)
    protected String unit;

    /**
     * Gets the value of the highValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getHighValue() {
        return highValue;
    }

    /**
     * Sets the value of the highValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setHighValue(Float value) {
        this.highValue = value;
    }

    /**
     * Gets the value of the lowValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getLowValue() {
        return lowValue;
    }

    /**
     * Sets the value of the lowValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setLowValue(Float value) {
        this.lowValue = value;
    }

    /**
     * Gets the value of the sensorTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSensorTypeId() {
        return sensorTypeId;
    }

    /**
     * Sets the value of the sensorTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSensorTypeId(Integer value) {
        this.sensorTypeId = value;
    }

    /**
     * Gets the value of the sensorTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSensorTypeName() {
        return sensorTypeName;
    }

    /**
     * Sets the value of the sensorTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSensorTypeName(String value) {
        this.sensorTypeName = value;
    }

    /**
     * Gets the value of the testUnitId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestUnitId() {
        return testUnitId;
    }

    /**
     * Sets the value of the testUnitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestUnitId(Integer value) {
        this.testUnitId = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

}
