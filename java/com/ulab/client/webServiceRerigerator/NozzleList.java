
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NozzleList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NozzleList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nozzleDiameter" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="nozzleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nozzleNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="standardName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testUnitNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NozzleList", namespace = "http://bean", propOrder = {
    "labCode",
    "nozzleDiameter",
    "nozzleName",
    "nozzleNo",
    "standardName",
    "testUnitNo"
})
public class NozzleList {

    @XmlElement(nillable = true)
    protected String labCode;
    protected Float nozzleDiameter;
    @XmlElement(nillable = true)
    protected String nozzleName;
    protected Integer nozzleNo;
    @XmlElement(nillable = true)
    protected String standardName;
    protected Integer testUnitNo;

    /**
     * Gets the value of the labCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabCode() {
        return labCode;
    }

    /**
     * Sets the value of the labCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabCode(String value) {
        this.labCode = value;
    }

    /**
     * Gets the value of the nozzleDiameter property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNozzleDiameter() {
        return nozzleDiameter;
    }

    /**
     * Sets the value of the nozzleDiameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNozzleDiameter(Float value) {
        this.nozzleDiameter = value;
    }

    /**
     * Gets the value of the nozzleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNozzleName() {
        return nozzleName;
    }

    /**
     * Sets the value of the nozzleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNozzleName(String value) {
        this.nozzleName = value;
    }

    /**
     * Gets the value of the nozzleNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNozzleNo() {
        return nozzleNo;
    }

    /**
     * Sets the value of the nozzleNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNozzleNo(Integer value) {
        this.nozzleNo = value;
    }

    /**
     * Gets the value of the standardName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardName() {
        return standardName;
    }

    /**
     * Sets the value of the standardName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardName(String value) {
        this.standardName = value;
    }

    /**
     * Gets the value of the testUnitNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestUnitNo() {
        return testUnitNo;
    }

    /**
     * Sets the value of the testUnitNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestUnitNo(Integer value) {
        this.testUnitNo = value;
    }

}
