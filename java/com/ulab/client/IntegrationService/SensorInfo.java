
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SensorInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SensorInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="coordinateno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="keyParameter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensorname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensorno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testunitno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SensorInfo", namespace = "http://bean", propOrder = {
    "coordinateno",
    "id",
    "keyParameter",
    "sensorname",
    "sensorno",
    "testunitno"
})
public class SensorInfo {

    protected Integer coordinateno;
    protected Integer id;
    @XmlElement(nillable = true)
    protected String keyParameter;
    @XmlElement(nillable = true)
    protected String sensorname;
    protected Integer sensorno;
    protected Integer testunitno;

    /**
     * Gets the value of the coordinateno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCoordinateno() {
        return coordinateno;
    }

    /**
     * Sets the value of the coordinateno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCoordinateno(Integer value) {
        this.coordinateno = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the keyParameter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyParameter() {
        return keyParameter;
    }

    /**
     * Sets the value of the keyParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyParameter(String value) {
        this.keyParameter = value;
    }

    /**
     * Gets the value of the sensorname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSensorname() {
        return sensorname;
    }

    /**
     * Sets the value of the sensorname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSensorname(String value) {
        this.sensorname = value;
    }

    /**
     * Gets the value of the sensorno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSensorno() {
        return sensorno;
    }

    /**
     * Sets the value of the sensorno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSensorno(Integer value) {
        this.sensorno = value;
    }

    /**
     * Gets the value of the testunitno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestunitno() {
        return testunitno;
    }

    /**
     * Sets the value of the testunitno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestunitno(Integer value) {
        this.testunitno = value;
    }

}
