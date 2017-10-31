
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocalWebServiceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocalWebServiceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accessMode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbServiceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="englishname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ifconfig" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="labcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocalWebServiceInfo", namespace = "http://bean", propOrder = {
    "accessMode",
    "address",
    "dbIp",
    "dbPassword",
    "dbServiceName",
    "dbUserName",
    "englishname",
    "id",
    "ifconfig",
    "labcode",
    "name",
    "port",
    "version"
})
public class LocalWebServiceInfo {

    protected Integer accessMode;
    @XmlElement(nillable = true)
    protected String address;
    @XmlElement(nillable = true)
    protected String dbIp;
    @XmlElement(nillable = true)
    protected String dbPassword;
    @XmlElement(nillable = true)
    protected String dbServiceName;
    @XmlElement(nillable = true)
    protected String dbUserName;
    @XmlElement(nillable = true)
    protected String englishname;
    protected Integer id;
    protected Integer ifconfig;
    @XmlElement(nillable = true)
    protected String labcode;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String port;
    @XmlElement(nillable = true)
    protected String version;

    /**
     * Gets the value of the accessMode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccessMode() {
        return accessMode;
    }

    /**
     * Sets the value of the accessMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccessMode(Integer value) {
        this.accessMode = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the dbIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbIp() {
        return dbIp;
    }

    /**
     * Sets the value of the dbIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbIp(String value) {
        this.dbIp = value;
    }

    /**
     * Gets the value of the dbPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * Sets the value of the dbPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbPassword(String value) {
        this.dbPassword = value;
    }

    /**
     * Gets the value of the dbServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbServiceName() {
        return dbServiceName;
    }

    /**
     * Sets the value of the dbServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbServiceName(String value) {
        this.dbServiceName = value;
    }

    /**
     * Gets the value of the dbUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbUserName() {
        return dbUserName;
    }

    /**
     * Sets the value of the dbUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbUserName(String value) {
        this.dbUserName = value;
    }

    /**
     * Gets the value of the englishname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnglishname() {
        return englishname;
    }

    /**
     * Sets the value of the englishname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnglishname(String value) {
        this.englishname = value;
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
     * Gets the value of the ifconfig property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIfconfig() {
        return ifconfig;
    }

    /**
     * Sets the value of the ifconfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIfconfig(Integer value) {
        this.ifconfig = value;
    }

    /**
     * Gets the value of the labcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabcode() {
        return labcode;
    }

    /**
     * Sets the value of the labcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabcode(String value) {
        this.labcode = value;
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
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
