
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TbStd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TbStd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="pdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stdName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TbStd", namespace = "http://bean", propOrder = {
    "code",
    "id",
    "pdType",
    "stdName",
    "stdType"
})
public class TbStd {

    @XmlElement(nillable = true)
    protected String code;
    protected Integer id;
    @XmlElement(nillable = true)
    protected String pdType;
    @XmlElement(nillable = true)
    protected String stdName;
    @XmlElement(nillable = true)
    protected String stdType;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
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
     * Gets the value of the pdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPdType() {
        return pdType;
    }

    /**
     * Sets the value of the pdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPdType(String value) {
        this.pdType = value;
    }

    /**
     * Gets the value of the stdName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStdName() {
        return stdName;
    }

    /**
     * Sets the value of the stdName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStdName(String value) {
        this.stdName = value;
    }

    /**
     * Gets the value of the stdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStdType() {
        return stdType;
    }

    /**
     * Sets the value of the stdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStdType(String value) {
        this.stdType = value;
    }

}
