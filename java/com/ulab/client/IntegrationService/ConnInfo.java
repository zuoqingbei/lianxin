
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConnInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConnInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auditTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="enWsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ifConfig" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="industrialParkId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="introduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="labCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loadrate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduleCompanyId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orderAll" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orderUnFinished" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ordering" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="overTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passWord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="picture" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="rdcId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userLabId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnInfo", namespace = "http://bean", propOrder = {
    "auditTime",
    "departId",
    "enWsName",
    "id",
    "ifConfig",
    "industrialParkId",
    "introduction",
    "labCode",
    "loadrate",
    "moduleCompanyId",
    "orderAll",
    "orderUnFinished",
    "ordering",
    "overTime",
    "passWord",
    "picture",
    "port",
    "productId",
    "rdcId",
    "url",
    "userLabId",
    "userName",
    "wsName"
})
public class ConnInfo {

    @XmlElement(nillable = true)
    protected String auditTime;
    protected Integer departId;
    @XmlElement(nillable = true)
    protected String enWsName;
    protected Integer id;
    protected Integer ifConfig;
    protected Integer industrialParkId;
    @XmlElement(nillable = true)
    protected String introduction;
    @XmlElement(nillable = true)
    protected String labCode;
    @XmlElement(nillable = true)
    protected String loadrate;
    protected Integer moduleCompanyId;
    protected Integer orderAll;
    protected Integer orderUnFinished;
    protected Integer ordering;
    @XmlElement(nillable = true)
    protected String overTime;
    @XmlElement(nillable = true)
    protected String passWord;
    @XmlElement(nillable = true)
    protected String picture;
    @XmlElement(nillable = true)
    protected String port;
    protected Integer productId;
    protected Integer rdcId;
    @XmlElement(nillable = true)
    protected String url;
    protected Integer userLabId;
    @XmlElement(nillable = true)
    protected String userName;
    @XmlElement(nillable = true)
    protected String wsName;

    /**
     * Gets the value of the auditTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditTime() {
        return auditTime;
    }

    /**
     * Sets the value of the auditTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditTime(String value) {
        this.auditTime = value;
    }

    /**
     * Gets the value of the departId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDepartId() {
        return departId;
    }

    /**
     * Sets the value of the departId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDepartId(Integer value) {
        this.departId = value;
    }

    /**
     * Gets the value of the enWsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnWsName() {
        return enWsName;
    }

    /**
     * Sets the value of the enWsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnWsName(String value) {
        this.enWsName = value;
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
     * Gets the value of the ifConfig property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIfConfig() {
        return ifConfig;
    }

    /**
     * Sets the value of the ifConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIfConfig(Integer value) {
        this.ifConfig = value;
    }

    /**
     * Gets the value of the industrialParkId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndustrialParkId() {
        return industrialParkId;
    }

    /**
     * Sets the value of the industrialParkId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndustrialParkId(Integer value) {
        this.industrialParkId = value;
    }

    /**
     * Gets the value of the introduction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * Sets the value of the introduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntroduction(String value) {
        this.introduction = value;
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
     * Gets the value of the loadrate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadrate() {
        return loadrate;
    }

    /**
     * Sets the value of the loadrate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadrate(String value) {
        this.loadrate = value;
    }

    /**
     * Gets the value of the moduleCompanyId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getModuleCompanyId() {
        return moduleCompanyId;
    }

    /**
     * Sets the value of the moduleCompanyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setModuleCompanyId(Integer value) {
        this.moduleCompanyId = value;
    }

    /**
     * Gets the value of the orderAll property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderAll() {
        return orderAll;
    }

    /**
     * Sets the value of the orderAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderAll(Integer value) {
        this.orderAll = value;
    }

    /**
     * Gets the value of the orderUnFinished property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderUnFinished() {
        return orderUnFinished;
    }

    /**
     * Sets the value of the orderUnFinished property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderUnFinished(Integer value) {
        this.orderUnFinished = value;
    }

    /**
     * Gets the value of the ordering property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrdering() {
        return ordering;
    }

    /**
     * Sets the value of the ordering property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrdering(Integer value) {
        this.ordering = value;
    }

    /**
     * Gets the value of the overTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverTime() {
        return overTime;
    }

    /**
     * Sets the value of the overTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverTime(String value) {
        this.overTime = value;
    }

    /**
     * Gets the value of the passWord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * Sets the value of the passWord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassWord(String value) {
        this.passWord = value;
    }

    /**
     * Gets the value of the picture property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the value of the picture property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicture(String value) {
        this.picture = value;
    }

    /**
     * Gets the value of the port property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPort(String value) {
        this.port = value;
    }

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProductId(Integer value) {
        this.productId = value;
    }

    /**
     * Gets the value of the rdcId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRdcId() {
        return rdcId;
    }

    /**
     * Sets the value of the rdcId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRdcId(Integer value) {
        this.rdcId = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the userLabId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUserLabId() {
        return userLabId;
    }

    /**
     * Sets the value of the userLabId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserLabId(Integer value) {
        this.userLabId = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the wsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWsName() {
        return wsName;
    }

    /**
     * Sets the value of the wsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWsName(String value) {
        this.wsName = value;
    }

}
