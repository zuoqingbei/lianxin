
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestMetadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="changeFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="inputContent" type="{http://mgr}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="isTesting" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noPowerLimit" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="testBeginTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testIdentification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testUnitId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestMetadata", namespace = "http://bean", propOrder = {
    "changeFlag",
    "inputContent",
    "isTesting",
    "labCode",
    "noPowerLimit",
    "testBeginTime",
    "testEndTime",
    "testIdentification",
    "testUnitId"
})
public class TestMetadata {

    protected Integer changeFlag;
    @XmlElement(nillable = true)
    protected ArrayOfString inputContent;
    protected Integer isTesting;
    @XmlElement(nillable = true)
    protected String labCode;
    protected Float noPowerLimit;
    @XmlElement(nillable = true)
    protected String testBeginTime;
    @XmlElement(nillable = true)
    protected String testEndTime;
    @XmlElement(nillable = true)
    protected String testIdentification;
    protected Integer testUnitId;

    /**
     * Gets the value of the changeFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChangeFlag() {
        return changeFlag;
    }

    /**
     * Sets the value of the changeFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChangeFlag(Integer value) {
        this.changeFlag = value;
    }

    /**
     * Gets the value of the inputContent property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getInputContent() {
        return inputContent;
    }

    /**
     * Sets the value of the inputContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setInputContent(ArrayOfString value) {
        this.inputContent = value;
    }

    /**
     * Gets the value of the isTesting property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsTesting() {
        return isTesting;
    }

    /**
     * Sets the value of the isTesting property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsTesting(Integer value) {
        this.isTesting = value;
    }

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
     * Gets the value of the noPowerLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getNoPowerLimit() {
        return noPowerLimit;
    }

    /**
     * Sets the value of the noPowerLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setNoPowerLimit(Float value) {
        this.noPowerLimit = value;
    }

    /**
     * Gets the value of the testBeginTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestBeginTime() {
        return testBeginTime;
    }

    /**
     * Sets the value of the testBeginTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestBeginTime(String value) {
        this.testBeginTime = value;
    }

    /**
     * Gets the value of the testEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestEndTime() {
        return testEndTime;
    }

    /**
     * Sets the value of the testEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestEndTime(String value) {
        this.testEndTime = value;
    }

    /**
     * Gets the value of the testIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestIdentification() {
        return testIdentification;
    }

    /**
     * Sets the value of the testIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestIdentification(String value) {
        this.testIdentification = value;
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

}
