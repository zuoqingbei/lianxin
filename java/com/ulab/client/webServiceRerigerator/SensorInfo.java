
package com.ulab.client.webServiceRerigerator;

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
 *         &lt;element name="assemblyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="averageSelect" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="commonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commonSelect" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="coordinateNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="coordinateNoStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="diffSelect" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dotNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="englishCommonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="englishName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="functionClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="functionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="functionParas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="integerAveSelect" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isReport" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isVirtual" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="keyParameter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxSelect" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="minSelect" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="selected" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="selectedStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensorId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sensorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensorTypeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sensorTypeStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testUnitId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="totalSequenceNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="versionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="visible" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="visibleStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "assemblyName",
    "averageSelect",
    "commonName",
    "commonSelect",
    "coordinateNo",
    "coordinateNoStr",
    "diffSelect",
    "dotNum",
    "englishCommonName",
    "englishName",
    "functionClass",
    "functionName",
    "functionParas",
    "groupNo",
    "integerAveSelect",
    "isReport",
    "isVirtual",
    "keyParameter",
    "labCode",
    "maxSelect",
    "minSelect",
    "selected",
    "selectedStr",
    "sensorId",
    "sensorName",
    "sensorTypeId",
    "sensorTypeStr",
    "state",
    "testUnitId",
    "totalSequenceNo",
    "versionNo",
    "visible",
    "visibleStr"
})
public class SensorInfo {

    @XmlElement(nillable = true)
    protected String assemblyName;
    protected Integer averageSelect;
    @XmlElement(nillable = true)
    protected String commonName;
    protected Integer commonSelect;
    protected Integer coordinateNo;
    @XmlElement(nillable = true)
    protected String coordinateNoStr;
    protected Integer diffSelect;
    @XmlElement(nillable = true)
    protected String dotNum;
    @XmlElement(nillable = true)
    protected String englishCommonName;
    @XmlElement(nillable = true)
    protected String englishName;
    @XmlElement(nillable = true)
    protected String functionClass;
    @XmlElement(nillable = true)
    protected String functionName;
    @XmlElement(nillable = true)
    protected String functionParas;
    protected Integer groupNo;
    protected Integer integerAveSelect;
    protected Integer isReport;
    protected Integer isVirtual;
    @XmlElement(nillable = true)
    protected String keyParameter;
    @XmlElement(nillable = true)
    protected String labCode;
    protected Integer maxSelect;
    protected Integer minSelect;
    protected Integer selected;
    @XmlElement(nillable = true)
    protected String selectedStr;
    protected Integer sensorId;
    @XmlElement(nillable = true)
    protected String sensorName;
    protected Integer sensorTypeId;
    @XmlElement(nillable = true)
    protected String sensorTypeStr;
    protected Integer state;
    protected Integer testUnitId;
    protected Integer totalSequenceNo;
    @XmlElement(nillable = true)
    protected String versionNo;
    protected Integer visible;
    @XmlElement(nillable = true)
    protected String visibleStr;

    /**
     * Gets the value of the assemblyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssemblyName() {
        return assemblyName;
    }

    /**
     * Sets the value of the assemblyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssemblyName(String value) {
        this.assemblyName = value;
    }

    /**
     * Gets the value of the averageSelect property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAverageSelect() {
        return averageSelect;
    }

    /**
     * Sets the value of the averageSelect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAverageSelect(Integer value) {
        this.averageSelect = value;
    }

    /**
     * Gets the value of the commonName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * Sets the value of the commonName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommonName(String value) {
        this.commonName = value;
    }

    /**
     * Gets the value of the commonSelect property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCommonSelect() {
        return commonSelect;
    }

    /**
     * Sets the value of the commonSelect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCommonSelect(Integer value) {
        this.commonSelect = value;
    }

    /**
     * Gets the value of the coordinateNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCoordinateNo() {
        return coordinateNo;
    }

    /**
     * Sets the value of the coordinateNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCoordinateNo(Integer value) {
        this.coordinateNo = value;
    }

    /**
     * Gets the value of the coordinateNoStr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoordinateNoStr() {
        return coordinateNoStr;
    }

    /**
     * Sets the value of the coordinateNoStr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoordinateNoStr(String value) {
        this.coordinateNoStr = value;
    }

    /**
     * Gets the value of the diffSelect property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiffSelect() {
        return diffSelect;
    }

    /**
     * Sets the value of the diffSelect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiffSelect(Integer value) {
        this.diffSelect = value;
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
     * Gets the value of the englishCommonName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnglishCommonName() {
        return englishCommonName;
    }

    /**
     * Sets the value of the englishCommonName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnglishCommonName(String value) {
        this.englishCommonName = value;
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
     * Gets the value of the functionClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionClass() {
        return functionClass;
    }

    /**
     * Sets the value of the functionClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionClass(String value) {
        this.functionClass = value;
    }

    /**
     * Gets the value of the functionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Sets the value of the functionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionName(String value) {
        this.functionName = value;
    }

    /**
     * Gets the value of the functionParas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionParas() {
        return functionParas;
    }

    /**
     * Sets the value of the functionParas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionParas(String value) {
        this.functionParas = value;
    }

    /**
     * Gets the value of the groupNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGroupNo() {
        return groupNo;
    }

    /**
     * Sets the value of the groupNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGroupNo(Integer value) {
        this.groupNo = value;
    }

    /**
     * Gets the value of the integerAveSelect property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIntegerAveSelect() {
        return integerAveSelect;
    }

    /**
     * Sets the value of the integerAveSelect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIntegerAveSelect(Integer value) {
        this.integerAveSelect = value;
    }

    /**
     * Gets the value of the isReport property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsReport() {
        return isReport;
    }

    /**
     * Sets the value of the isReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsReport(Integer value) {
        this.isReport = value;
    }

    /**
     * Gets the value of the isVirtual property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsVirtual() {
        return isVirtual;
    }

    /**
     * Sets the value of the isVirtual property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsVirtual(Integer value) {
        this.isVirtual = value;
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
     * Gets the value of the maxSelect property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxSelect() {
        return maxSelect;
    }

    /**
     * Sets the value of the maxSelect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxSelect(Integer value) {
        this.maxSelect = value;
    }

    /**
     * Gets the value of the minSelect property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinSelect() {
        return minSelect;
    }

    /**
     * Sets the value of the minSelect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinSelect(Integer value) {
        this.minSelect = value;
    }

    /**
     * Gets the value of the selected property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSelected() {
        return selected;
    }

    /**
     * Sets the value of the selected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSelected(Integer value) {
        this.selected = value;
    }

    /**
     * Gets the value of the selectedStr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectedStr() {
        return selectedStr;
    }

    /**
     * Sets the value of the selectedStr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectedStr(String value) {
        this.selectedStr = value;
    }

    /**
     * Gets the value of the sensorId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSensorId() {
        return sensorId;
    }

    /**
     * Sets the value of the sensorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSensorId(Integer value) {
        this.sensorId = value;
    }

    /**
     * Gets the value of the sensorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Sets the value of the sensorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSensorName(String value) {
        this.sensorName = value;
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
     * Gets the value of the sensorTypeStr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSensorTypeStr() {
        return sensorTypeStr;
    }

    /**
     * Sets the value of the sensorTypeStr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSensorTypeStr(String value) {
        this.sensorTypeStr = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setState(Integer value) {
        this.state = value;
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
     * Gets the value of the totalSequenceNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalSequenceNo() {
        return totalSequenceNo;
    }

    /**
     * Sets the value of the totalSequenceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalSequenceNo(Integer value) {
        this.totalSequenceNo = value;
    }

    /**
     * Gets the value of the versionNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * Sets the value of the versionNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionNo(String value) {
        this.versionNo = value;
    }

    /**
     * Gets the value of the visible property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVisible() {
        return visible;
    }

    /**
     * Sets the value of the visible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVisible(Integer value) {
        this.visible = value;
    }

    /**
     * Gets the value of the visibleStr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisibleStr() {
        return visibleStr;
    }

    /**
     * Sets the value of the visibleStr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisibleStr(String value) {
        this.visibleStr = value;
    }

}
