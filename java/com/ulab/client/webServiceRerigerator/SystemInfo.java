
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SystemInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SystemInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="commonSensorNum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentCoordinateConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentSensorConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentTestsProdinfoItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayTimeLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="englishLabName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="englishSoftwareName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="englishTestUnitNameConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="infoQueryTimeLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="inputLink" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="labName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="noPowerLimit" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="reportPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensorNum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="softwareName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testCollectionPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testTable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testUnitNameConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testUnitNum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemInfo", namespace = "http://bean", propOrder = {
    "category",
    "commonSensorNum",
    "companyName",
    "currentCoordinateConfig",
    "currentSensorConfig",
    "currentTestsProdinfoItem",
    "displayTimeLimit",
    "englishLabName",
    "englishSoftwareName",
    "englishTestUnitNameConfig",
    "infoQueryTimeLimit",
    "inputLink",
    "labCode",
    "labName",
    "language",
    "noPowerLimit",
    "reportPassword",
    "sensorNum",
    "softwareName",
    "testCollectionPassword",
    "testTable",
    "testUnitNameConfig",
    "testUnitNum"
})
public class SystemInfo {

    protected Integer category;
    protected Integer commonSensorNum;
    @XmlElement(nillable = true)
    protected String companyName;
    @XmlElement(nillable = true)
    protected String currentCoordinateConfig;
    @XmlElement(nillable = true)
    protected String currentSensorConfig;
    @XmlElement(nillable = true)
    protected String currentTestsProdinfoItem;
    protected Integer displayTimeLimit;
    @XmlElement(nillable = true)
    protected String englishLabName;
    @XmlElement(nillable = true)
    protected String englishSoftwareName;
    @XmlElement(nillable = true)
    protected String englishTestUnitNameConfig;
    protected Integer infoQueryTimeLimit;
    protected Integer inputLink;
    @XmlElement(nillable = true)
    protected String labCode;
    @XmlElement(nillable = true)
    protected String labName;
    protected Integer language;
    protected Float noPowerLimit;
    @XmlElement(nillable = true)
    protected String reportPassword;
    protected Integer sensorNum;
    @XmlElement(nillable = true)
    protected String softwareName;
    @XmlElement(nillable = true)
    protected String testCollectionPassword;
    @XmlElement(nillable = true)
    protected String testTable;
    @XmlElement(nillable = true)
    protected String testUnitNameConfig;
    protected Integer testUnitNum;

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCategory(Integer value) {
        this.category = value;
    }

    /**
     * Gets the value of the commonSensorNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCommonSensorNum() {
        return commonSensorNum;
    }

    /**
     * Sets the value of the commonSensorNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCommonSensorNum(Integer value) {
        this.commonSensorNum = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the currentCoordinateConfig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentCoordinateConfig() {
        return currentCoordinateConfig;
    }

    /**
     * Sets the value of the currentCoordinateConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentCoordinateConfig(String value) {
        this.currentCoordinateConfig = value;
    }

    /**
     * Gets the value of the currentSensorConfig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentSensorConfig() {
        return currentSensorConfig;
    }

    /**
     * Sets the value of the currentSensorConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentSensorConfig(String value) {
        this.currentSensorConfig = value;
    }

    /**
     * Gets the value of the currentTestsProdinfoItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentTestsProdinfoItem() {
        return currentTestsProdinfoItem;
    }

    /**
     * Sets the value of the currentTestsProdinfoItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentTestsProdinfoItem(String value) {
        this.currentTestsProdinfoItem = value;
    }

    /**
     * Gets the value of the displayTimeLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDisplayTimeLimit() {
        return displayTimeLimit;
    }

    /**
     * Sets the value of the displayTimeLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDisplayTimeLimit(Integer value) {
        this.displayTimeLimit = value;
    }

    /**
     * Gets the value of the englishLabName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnglishLabName() {
        return englishLabName;
    }

    /**
     * Sets the value of the englishLabName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnglishLabName(String value) {
        this.englishLabName = value;
    }

    /**
     * Gets the value of the englishSoftwareName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnglishSoftwareName() {
        return englishSoftwareName;
    }

    /**
     * Sets the value of the englishSoftwareName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnglishSoftwareName(String value) {
        this.englishSoftwareName = value;
    }

    /**
     * Gets the value of the englishTestUnitNameConfig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnglishTestUnitNameConfig() {
        return englishTestUnitNameConfig;
    }

    /**
     * Sets the value of the englishTestUnitNameConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnglishTestUnitNameConfig(String value) {
        this.englishTestUnitNameConfig = value;
    }

    /**
     * Gets the value of the infoQueryTimeLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInfoQueryTimeLimit() {
        return infoQueryTimeLimit;
    }

    /**
     * Sets the value of the infoQueryTimeLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInfoQueryTimeLimit(Integer value) {
        this.infoQueryTimeLimit = value;
    }

    /**
     * Gets the value of the inputLink property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInputLink() {
        return inputLink;
    }

    /**
     * Sets the value of the inputLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInputLink(Integer value) {
        this.inputLink = value;
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
     * Gets the value of the labName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabName() {
        return labName;
    }

    /**
     * Sets the value of the labName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabName(String value) {
        this.labName = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLanguage(Integer value) {
        this.language = value;
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
     * Gets the value of the reportPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportPassword() {
        return reportPassword;
    }

    /**
     * Sets the value of the reportPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportPassword(String value) {
        this.reportPassword = value;
    }

    /**
     * Gets the value of the sensorNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSensorNum() {
        return sensorNum;
    }

    /**
     * Sets the value of the sensorNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSensorNum(Integer value) {
        this.sensorNum = value;
    }

    /**
     * Gets the value of the softwareName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareName() {
        return softwareName;
    }

    /**
     * Sets the value of the softwareName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareName(String value) {
        this.softwareName = value;
    }

    /**
     * Gets the value of the testCollectionPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestCollectionPassword() {
        return testCollectionPassword;
    }

    /**
     * Sets the value of the testCollectionPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestCollectionPassword(String value) {
        this.testCollectionPassword = value;
    }

    /**
     * Gets the value of the testTable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestTable() {
        return testTable;
    }

    /**
     * Sets the value of the testTable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestTable(String value) {
        this.testTable = value;
    }

    /**
     * Gets the value of the testUnitNameConfig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestUnitNameConfig() {
        return testUnitNameConfig;
    }

    /**
     * Sets the value of the testUnitNameConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestUnitNameConfig(String value) {
        this.testUnitNameConfig = value;
    }

    /**
     * Gets the value of the testUnitNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestUnitNum() {
        return testUnitNum;
    }

    /**
     * Sets the value of the testUnitNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestUnitNum(Integer value) {
        this.testUnitNum = value;
    }

}
