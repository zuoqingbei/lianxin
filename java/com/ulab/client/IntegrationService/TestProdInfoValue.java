
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestProdInfoValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestProdInfoValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="airSwitchPower" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="beginDateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="coordinateConfigVersionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="defrostingPower" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="defrostingPowerFactor" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="defrostingTemperature" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="deleteFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="displayTimeHigh" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="displayTimeLow" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="endDateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_25" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_26" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_27" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_28" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_29" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_30" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemContent_9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nopowerLimit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="primaryKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectMissionBookId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reportTime" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sensorConfigVersionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="steadyBeginTime" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="steadyTime" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="testItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testNow" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testProdInfoItemVersionNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "TestProdInfoValue", namespace = "http://bean", propOrder = {
    "airSwitchPower",
    "beginDateTime",
    "changeFlag",
    "coordinateConfigVersionNo",
    "defrostingPower",
    "defrostingPowerFactor",
    "defrostingTemperature",
    "deleteFlag",
    "displayTimeHigh",
    "displayTimeLow",
    "endDateTime",
    "itemContent1",
    "itemContent10",
    "itemContent11",
    "itemContent12",
    "itemContent13",
    "itemContent14",
    "itemContent15",
    "itemContent16",
    "itemContent17",
    "itemContent18",
    "itemContent19",
    "itemContent2",
    "itemContent20",
    "itemContent21",
    "itemContent22",
    "itemContent23",
    "itemContent24",
    "itemContent25",
    "itemContent26",
    "itemContent27",
    "itemContent28",
    "itemContent29",
    "itemContent3",
    "itemContent30",
    "itemContent4",
    "itemContent5",
    "itemContent6",
    "itemContent7",
    "itemContent8",
    "itemContent9",
    "labCode",
    "nopowerLimit",
    "primaryKey",
    "projectMissionBookId",
    "reportTime",
    "sensorConfigVersionNo",
    "steadyBeginTime",
    "steadyTime",
    "testItemId",
    "testNow",
    "testProdInfoItemVersionNo",
    "testUnitNo"
})
public class TestProdInfoValue {

    protected Double airSwitchPower;
    @XmlElement(nillable = true)
    protected String beginDateTime;
    protected Integer changeFlag;
    @XmlElement(nillable = true)
    protected String coordinateConfigVersionNo;
    protected Double defrostingPower;
    protected Double defrostingPowerFactor;
    protected Double defrostingTemperature;
    protected Integer deleteFlag;
    protected Double displayTimeHigh;
    protected Double displayTimeLow;
    @XmlElement(nillable = true)
    protected String endDateTime;
    @XmlElement(name = "itemContent_1", nillable = true)
    protected String itemContent1;
    @XmlElement(name = "itemContent_10", nillable = true)
    protected String itemContent10;
    @XmlElement(name = "itemContent_11", nillable = true)
    protected String itemContent11;
    @XmlElement(name = "itemContent_12", nillable = true)
    protected String itemContent12;
    @XmlElement(name = "itemContent_13", nillable = true)
    protected String itemContent13;
    @XmlElement(name = "itemContent_14", nillable = true)
    protected String itemContent14;
    @XmlElement(name = "itemContent_15", nillable = true)
    protected String itemContent15;
    @XmlElement(name = "itemContent_16", nillable = true)
    protected String itemContent16;
    @XmlElement(name = "itemContent_17", nillable = true)
    protected String itemContent17;
    @XmlElement(name = "itemContent_18", nillable = true)
    protected String itemContent18;
    @XmlElement(name = "itemContent_19", nillable = true)
    protected String itemContent19;
    @XmlElement(name = "itemContent_2", nillable = true)
    protected String itemContent2;
    @XmlElement(name = "itemContent_20", nillable = true)
    protected String itemContent20;
    @XmlElement(name = "itemContent_21", nillable = true)
    protected String itemContent21;
    @XmlElement(name = "itemContent_22", nillable = true)
    protected String itemContent22;
    @XmlElement(name = "itemContent_23", nillable = true)
    protected String itemContent23;
    @XmlElement(name = "itemContent_24", nillable = true)
    protected String itemContent24;
    @XmlElement(name = "itemContent_25", nillable = true)
    protected String itemContent25;
    @XmlElement(name = "itemContent_26", nillable = true)
    protected String itemContent26;
    @XmlElement(name = "itemContent_27", nillable = true)
    protected String itemContent27;
    @XmlElement(name = "itemContent_28", nillable = true)
    protected String itemContent28;
    @XmlElement(name = "itemContent_29", nillable = true)
    protected String itemContent29;
    @XmlElement(name = "itemContent_3", nillable = true)
    protected String itemContent3;
    @XmlElement(name = "itemContent_30", nillable = true)
    protected String itemContent30;
    @XmlElement(name = "itemContent_4", nillable = true)
    protected String itemContent4;
    @XmlElement(name = "itemContent_5", nillable = true)
    protected String itemContent5;
    @XmlElement(name = "itemContent_6", nillable = true)
    protected String itemContent6;
    @XmlElement(name = "itemContent_7", nillable = true)
    protected String itemContent7;
    @XmlElement(name = "itemContent_8", nillable = true)
    protected String itemContent8;
    @XmlElement(name = "itemContent_9", nillable = true)
    protected String itemContent9;
    @XmlElement(nillable = true)
    protected String labCode;
    protected Double nopowerLimit;
    @XmlElement(nillable = true)
    protected String primaryKey;
    protected Integer projectMissionBookId;
    protected Double reportTime;
    @XmlElement(nillable = true)
    protected String sensorConfigVersionNo;
    protected Double steadyBeginTime;
    protected Double steadyTime;
    @XmlElement(nillable = true)
    protected String testItemId;
    protected Integer testNow;
    @XmlElement(nillable = true)
    protected String testProdInfoItemVersionNo;
    protected Integer testUnitNo;

    /**
     * Gets the value of the airSwitchPower property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAirSwitchPower() {
        return airSwitchPower;
    }

    /**
     * Sets the value of the airSwitchPower property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAirSwitchPower(Double value) {
        this.airSwitchPower = value;
    }

    /**
     * Gets the value of the beginDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeginDateTime() {
        return beginDateTime;
    }

    /**
     * Sets the value of the beginDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeginDateTime(String value) {
        this.beginDateTime = value;
    }

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
     * Gets the value of the coordinateConfigVersionNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoordinateConfigVersionNo() {
        return coordinateConfigVersionNo;
    }

    /**
     * Sets the value of the coordinateConfigVersionNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoordinateConfigVersionNo(String value) {
        this.coordinateConfigVersionNo = value;
    }

    /**
     * Gets the value of the defrostingPower property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDefrostingPower() {
        return defrostingPower;
    }

    /**
     * Sets the value of the defrostingPower property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDefrostingPower(Double value) {
        this.defrostingPower = value;
    }

    /**
     * Gets the value of the defrostingPowerFactor property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDefrostingPowerFactor() {
        return defrostingPowerFactor;
    }

    /**
     * Sets the value of the defrostingPowerFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDefrostingPowerFactor(Double value) {
        this.defrostingPowerFactor = value;
    }

    /**
     * Gets the value of the defrostingTemperature property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDefrostingTemperature() {
        return defrostingTemperature;
    }

    /**
     * Sets the value of the defrostingTemperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDefrostingTemperature(Double value) {
        this.defrostingTemperature = value;
    }

    /**
     * Gets the value of the deleteFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the value of the deleteFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeleteFlag(Integer value) {
        this.deleteFlag = value;
    }

    /**
     * Gets the value of the displayTimeHigh property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDisplayTimeHigh() {
        return displayTimeHigh;
    }

    /**
     * Sets the value of the displayTimeHigh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDisplayTimeHigh(Double value) {
        this.displayTimeHigh = value;
    }

    /**
     * Gets the value of the displayTimeLow property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDisplayTimeLow() {
        return displayTimeLow;
    }

    /**
     * Sets the value of the displayTimeLow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDisplayTimeLow(Double value) {
        this.displayTimeLow = value;
    }

    /**
     * Gets the value of the endDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets the value of the endDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDateTime(String value) {
        this.endDateTime = value;
    }

    /**
     * Gets the value of the itemContent1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent1() {
        return itemContent1;
    }

    /**
     * Sets the value of the itemContent1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent1(String value) {
        this.itemContent1 = value;
    }

    /**
     * Gets the value of the itemContent10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent10() {
        return itemContent10;
    }

    /**
     * Sets the value of the itemContent10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent10(String value) {
        this.itemContent10 = value;
    }

    /**
     * Gets the value of the itemContent11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent11() {
        return itemContent11;
    }

    /**
     * Sets the value of the itemContent11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent11(String value) {
        this.itemContent11 = value;
    }

    /**
     * Gets the value of the itemContent12 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent12() {
        return itemContent12;
    }

    /**
     * Sets the value of the itemContent12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent12(String value) {
        this.itemContent12 = value;
    }

    /**
     * Gets the value of the itemContent13 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent13() {
        return itemContent13;
    }

    /**
     * Sets the value of the itemContent13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent13(String value) {
        this.itemContent13 = value;
    }

    /**
     * Gets the value of the itemContent14 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent14() {
        return itemContent14;
    }

    /**
     * Sets the value of the itemContent14 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent14(String value) {
        this.itemContent14 = value;
    }

    /**
     * Gets the value of the itemContent15 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent15() {
        return itemContent15;
    }

    /**
     * Sets the value of the itemContent15 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent15(String value) {
        this.itemContent15 = value;
    }

    /**
     * Gets the value of the itemContent16 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent16() {
        return itemContent16;
    }

    /**
     * Sets the value of the itemContent16 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent16(String value) {
        this.itemContent16 = value;
    }

    /**
     * Gets the value of the itemContent17 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent17() {
        return itemContent17;
    }

    /**
     * Sets the value of the itemContent17 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent17(String value) {
        this.itemContent17 = value;
    }

    /**
     * Gets the value of the itemContent18 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent18() {
        return itemContent18;
    }

    /**
     * Sets the value of the itemContent18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent18(String value) {
        this.itemContent18 = value;
    }

    /**
     * Gets the value of the itemContent19 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent19() {
        return itemContent19;
    }

    /**
     * Sets the value of the itemContent19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent19(String value) {
        this.itemContent19 = value;
    }

    /**
     * Gets the value of the itemContent2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent2() {
        return itemContent2;
    }

    /**
     * Sets the value of the itemContent2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent2(String value) {
        this.itemContent2 = value;
    }

    /**
     * Gets the value of the itemContent20 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent20() {
        return itemContent20;
    }

    /**
     * Sets the value of the itemContent20 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent20(String value) {
        this.itemContent20 = value;
    }

    /**
     * Gets the value of the itemContent21 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent21() {
        return itemContent21;
    }

    /**
     * Sets the value of the itemContent21 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent21(String value) {
        this.itemContent21 = value;
    }

    /**
     * Gets the value of the itemContent22 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent22() {
        return itemContent22;
    }

    /**
     * Sets the value of the itemContent22 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent22(String value) {
        this.itemContent22 = value;
    }

    /**
     * Gets the value of the itemContent23 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent23() {
        return itemContent23;
    }

    /**
     * Sets the value of the itemContent23 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent23(String value) {
        this.itemContent23 = value;
    }

    /**
     * Gets the value of the itemContent24 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent24() {
        return itemContent24;
    }

    /**
     * Sets the value of the itemContent24 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent24(String value) {
        this.itemContent24 = value;
    }

    /**
     * Gets the value of the itemContent25 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent25() {
        return itemContent25;
    }

    /**
     * Sets the value of the itemContent25 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent25(String value) {
        this.itemContent25 = value;
    }

    /**
     * Gets the value of the itemContent26 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent26() {
        return itemContent26;
    }

    /**
     * Sets the value of the itemContent26 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent26(String value) {
        this.itemContent26 = value;
    }

    /**
     * Gets the value of the itemContent27 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent27() {
        return itemContent27;
    }

    /**
     * Sets the value of the itemContent27 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent27(String value) {
        this.itemContent27 = value;
    }

    /**
     * Gets the value of the itemContent28 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent28() {
        return itemContent28;
    }

    /**
     * Sets the value of the itemContent28 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent28(String value) {
        this.itemContent28 = value;
    }

    /**
     * Gets the value of the itemContent29 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent29() {
        return itemContent29;
    }

    /**
     * Sets the value of the itemContent29 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent29(String value) {
        this.itemContent29 = value;
    }

    /**
     * Gets the value of the itemContent3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent3() {
        return itemContent3;
    }

    /**
     * Sets the value of the itemContent3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent3(String value) {
        this.itemContent3 = value;
    }

    /**
     * Gets the value of the itemContent30 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent30() {
        return itemContent30;
    }

    /**
     * Sets the value of the itemContent30 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent30(String value) {
        this.itemContent30 = value;
    }

    /**
     * Gets the value of the itemContent4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent4() {
        return itemContent4;
    }

    /**
     * Sets the value of the itemContent4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent4(String value) {
        this.itemContent4 = value;
    }

    /**
     * Gets the value of the itemContent5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent5() {
        return itemContent5;
    }

    /**
     * Sets the value of the itemContent5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent5(String value) {
        this.itemContent5 = value;
    }

    /**
     * Gets the value of the itemContent6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent6() {
        return itemContent6;
    }

    /**
     * Sets the value of the itemContent6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent6(String value) {
        this.itemContent6 = value;
    }

    /**
     * Gets the value of the itemContent7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent7() {
        return itemContent7;
    }

    /**
     * Sets the value of the itemContent7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent7(String value) {
        this.itemContent7 = value;
    }

    /**
     * Gets the value of the itemContent8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent8() {
        return itemContent8;
    }

    /**
     * Sets the value of the itemContent8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent8(String value) {
        this.itemContent8 = value;
    }

    /**
     * Gets the value of the itemContent9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemContent9() {
        return itemContent9;
    }

    /**
     * Sets the value of the itemContent9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemContent9(String value) {
        this.itemContent9 = value;
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
     * Gets the value of the nopowerLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNopowerLimit() {
        return nopowerLimit;
    }

    /**
     * Sets the value of the nopowerLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNopowerLimit(Double value) {
        this.nopowerLimit = value;
    }

    /**
     * Gets the value of the primaryKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the value of the primaryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryKey(String value) {
        this.primaryKey = value;
    }

    /**
     * Gets the value of the projectMissionBookId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProjectMissionBookId() {
        return projectMissionBookId;
    }

    /**
     * Sets the value of the projectMissionBookId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProjectMissionBookId(Integer value) {
        this.projectMissionBookId = value;
    }

    /**
     * Gets the value of the reportTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getReportTime() {
        return reportTime;
    }

    /**
     * Sets the value of the reportTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setReportTime(Double value) {
        this.reportTime = value;
    }

    /**
     * Gets the value of the sensorConfigVersionNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSensorConfigVersionNo() {
        return sensorConfigVersionNo;
    }

    /**
     * Sets the value of the sensorConfigVersionNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSensorConfigVersionNo(String value) {
        this.sensorConfigVersionNo = value;
    }

    /**
     * Gets the value of the steadyBeginTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSteadyBeginTime() {
        return steadyBeginTime;
    }

    /**
     * Sets the value of the steadyBeginTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSteadyBeginTime(Double value) {
        this.steadyBeginTime = value;
    }

    /**
     * Gets the value of the steadyTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSteadyTime() {
        return steadyTime;
    }

    /**
     * Sets the value of the steadyTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSteadyTime(Double value) {
        this.steadyTime = value;
    }

    /**
     * Gets the value of the testItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestItemId() {
        return testItemId;
    }

    /**
     * Sets the value of the testItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestItemId(String value) {
        this.testItemId = value;
    }

    /**
     * Gets the value of the testNow property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestNow() {
        return testNow;
    }

    /**
     * Sets the value of the testNow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestNow(Integer value) {
        this.testNow = value;
    }

    /**
     * Gets the value of the testProdInfoItemVersionNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestProdInfoItemVersionNo() {
        return testProdInfoItemVersionNo;
    }

    /**
     * Sets the value of the testProdInfoItemVersionNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestProdInfoItemVersionNo(String value) {
        this.testProdInfoItemVersionNo = value;
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
