
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestUnitConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestUnitConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="belongedId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="borrowInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataBufferLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="diffMode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="englishName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupInfoDefault" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ifBorrow" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isGroupInfoDefault" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testUnitName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testUnitNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testnow" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestUnitConfig", namespace = "http://bean", propOrder = {
    "belongedId",
    "borrowInfo",
    "dataBufferLength",
    "diffMode",
    "englishName",
    "groupInfoDefault",
    "ifBorrow",
    "isGroupInfoDefault",
    "labCode",
    "testType",
    "testUnitName",
    "testUnitNo",
    "testnow"
})
public class TestUnitConfig {

    protected Integer belongedId;
    @XmlElement(nillable = true)
    protected String borrowInfo;
    protected Integer dataBufferLength;
    protected Integer diffMode;
    @XmlElement(nillable = true)
    protected String englishName;
    @XmlElement(nillable = true)
    protected String groupInfoDefault;
    protected Integer ifBorrow;
    protected Integer isGroupInfoDefault;
    @XmlElement(nillable = true)
    protected String labCode;
    protected Integer testType;
    @XmlElement(nillable = true)
    protected String testUnitName;
    protected Integer testUnitNo;
    protected Integer testnow;

    /**
     * Gets the value of the belongedId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBelongedId() {
        return belongedId;
    }

    /**
     * Sets the value of the belongedId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBelongedId(Integer value) {
        this.belongedId = value;
    }

    /**
     * Gets the value of the borrowInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorrowInfo() {
        return borrowInfo;
    }

    /**
     * Sets the value of the borrowInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorrowInfo(String value) {
        this.borrowInfo = value;
    }

    /**
     * Gets the value of the dataBufferLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDataBufferLength() {
        return dataBufferLength;
    }

    /**
     * Sets the value of the dataBufferLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDataBufferLength(Integer value) {
        this.dataBufferLength = value;
    }

    /**
     * Gets the value of the diffMode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiffMode() {
        return diffMode;
    }

    /**
     * Sets the value of the diffMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiffMode(Integer value) {
        this.diffMode = value;
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
     * Gets the value of the groupInfoDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupInfoDefault() {
        return groupInfoDefault;
    }

    /**
     * Sets the value of the groupInfoDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupInfoDefault(String value) {
        this.groupInfoDefault = value;
    }

    /**
     * Gets the value of the ifBorrow property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIfBorrow() {
        return ifBorrow;
    }

    /**
     * Sets the value of the ifBorrow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIfBorrow(Integer value) {
        this.ifBorrow = value;
    }

    /**
     * Gets the value of the isGroupInfoDefault property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsGroupInfoDefault() {
        return isGroupInfoDefault;
    }

    /**
     * Sets the value of the isGroupInfoDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsGroupInfoDefault(Integer value) {
        this.isGroupInfoDefault = value;
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
     * Gets the value of the testType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestType() {
        return testType;
    }

    /**
     * Sets the value of the testType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestType(Integer value) {
        this.testType = value;
    }

    /**
     * Gets the value of the testUnitName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestUnitName() {
        return testUnitName;
    }

    /**
     * Sets the value of the testUnitName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestUnitName(String value) {
        this.testUnitName = value;
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

    /**
     * Gets the value of the testnow property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestnow() {
        return testnow;
    }

    /**
     * Sets the value of the testnow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestnow(Integer value) {
        this.testnow = value;
    }

}
