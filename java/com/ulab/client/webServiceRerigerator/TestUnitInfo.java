
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestUnitInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestUnitInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="belongId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="borrowInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="diffMode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="englishName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupInfoDefault" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ifBorrow" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isGroupINfoDefault" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testUnitId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testUnitName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestUnitInfo", namespace = "http://bean", propOrder = {
    "belongId",
    "borrowInfo",
    "diffMode",
    "englishName",
    "groupInfoDefault",
    "ifBorrow",
    "isGroupINfoDefault",
    "labCode",
    "testType",
    "testUnitId",
    "testUnitName"
})
public class TestUnitInfo {

    protected Integer belongId;
    @XmlElement(nillable = true)
    protected String borrowInfo;
    protected Integer diffMode;
    @XmlElement(nillable = true)
    protected String englishName;
    @XmlElement(nillable = true)
    protected String groupInfoDefault;
    protected Integer ifBorrow;
    protected Integer isGroupINfoDefault;
    @XmlElement(nillable = true)
    protected String labCode;
    protected Integer testType;
    protected Integer testUnitId;
    @XmlElement(nillable = true)
    protected String testUnitName;

    /**
     * Gets the value of the belongId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBelongId() {
        return belongId;
    }

    /**
     * Sets the value of the belongId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBelongId(Integer value) {
        this.belongId = value;
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
     * Gets the value of the isGroupINfoDefault property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsGroupINfoDefault() {
        return isGroupINfoDefault;
    }

    /**
     * Sets the value of the isGroupINfoDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsGroupINfoDefault(Integer value) {
        this.isGroupINfoDefault = value;
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

}
