
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserLab complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserLab">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="departId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="labcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="labname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="webserviceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="webserviceurl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserLab", namespace = "http://bean", propOrder = {
    "departId",
    "id",
    "labcode",
    "labname",
    "webserviceId",
    "webserviceurl"
})
public class UserLab {

    protected Integer departId;
    protected Integer id;
    @XmlElement(nillable = true)
    protected String labcode;
    @XmlElement(nillable = true)
    protected String labname;
    protected Integer webserviceId;
    @XmlElement(nillable = true)
    protected String webserviceurl;

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
     * Gets the value of the labname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabname() {
        return labname;
    }

    /**
     * Sets the value of the labname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabname(String value) {
        this.labname = value;
    }

    /**
     * Gets the value of the webserviceId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWebserviceId() {
        return webserviceId;
    }

    /**
     * Sets the value of the webserviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWebserviceId(Integer value) {
        this.webserviceId = value;
    }

    /**
     * Gets the value of the webserviceurl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebserviceurl() {
        return webserviceurl;
    }

    /**
     * Sets the value of the webserviceurl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebserviceurl(String value) {
        this.webserviceurl = value;
    }

}
