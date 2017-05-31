
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestWorkingCondition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestWorkingCondition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allowableDifference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dotNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="englishName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="globalSensorNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isUseSteady" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="no" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="standardName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testUnitNavigationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="workingCondition" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestWorkingCondition", namespace = "http://bean", propOrder = {
    "allowableDifference",
    "dotNum",
    "englishName",
    "globalSensorNo",
    "isUseSteady",
    "labCode",
    "name",
    "no",
    "standardName",
    "testUnitNavigationId",
    "unit",
    "value",
    "workingCondition"
})
public class TestWorkingCondition {

    @XmlElement(nillable = true)
    protected String allowableDifference;
    @XmlElement(nillable = true)
    protected String dotNum;
    @XmlElement(nillable = true)
    protected String englishName;
    protected Integer globalSensorNo;
    protected Integer isUseSteady;
    @XmlElement(nillable = true)
    protected String labCode;
    @XmlElement(nillable = true)
    protected String name;
    protected Integer no;
    @XmlElement(nillable = true)
    protected String standardName;
    protected Integer testUnitNavigationId;
    @XmlElement(nillable = true)
    protected String unit;
    protected Float value;
    protected Float workingCondition;

    /**
     * Gets the value of the allowableDifference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllowableDifference() {
        return allowableDifference;
    }

    /**
     * Sets the value of the allowableDifference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllowableDifference(String value) {
        this.allowableDifference = value;
    }

    /**
     * Gets the value of the dotNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDotNum() {
        return dotNum;
    }

    /**
     * Sets the value of the dotNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDotNum(String value) {
        this.dotNum = value;
    }

    /**
     * Gets the value of the englishName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * Sets the value of the englishName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnglishName(String value) {
        this.englishName = value;
    }

    /**
     * Gets the value of the globalSensorNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGlobalSensorNo() {
        return globalSensorNo;
    }

    /**
     * Sets the value of the globalSensorNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGlobalSensorNo(Integer value) {
        this.globalSensorNo = value;
    }

    /**
     * Gets the value of the isUseSteady property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsUseSteady() {
        return isUseSteady;
    }

    /**
     * Sets the value of the isUseSteady property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsUseSteady(Integer value) {
        this.isUseSteady = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the no property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNo() {
        return no;
    }

    /**
     * Sets the value of the no property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNo(Integer value) {
        this.no = value;
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
     * Gets the value of the testUnitNavigationId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestUnitNavigationId() {
        return testUnitNavigationId;
    }

    /**
     * Sets the value of the testUnitNavigationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestUnitNavigationId(Integer value) {
        this.testUnitNavigationId = value;
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

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setValue(Float value) {
        this.value = value;
    }

    /**
     * Gets the value of the workingCondition property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getWorkingCondition() {
        return workingCondition;
    }

    /**
     * Sets the value of the workingCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setWorkingCondition(Float value) {
        this.workingCondition = value;
    }

}
