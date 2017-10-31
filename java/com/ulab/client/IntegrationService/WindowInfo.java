
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WindowInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WindowInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="lowerlimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="testunitno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="upperlimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="visible" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="windowname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="windowno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WindowInfo", namespace = "http://bean", propOrder = {
    "id",
    "lowerlimit",
    "testunitno",
    "upperlimit",
    "visible",
    "windowname",
    "windowno"
})
public class WindowInfo {

    protected Integer id;
    protected Integer lowerlimit;
    protected Integer testunitno;
    protected Integer upperlimit;
    protected Integer visible;
    @XmlElement(nillable = true)
    protected String windowname;
    protected Integer windowno;

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
     * Gets the value of the lowerlimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLowerlimit() {
        return lowerlimit;
    }

    /**
     * Sets the value of the lowerlimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLowerlimit(Integer value) {
        this.lowerlimit = value;
    }

    /**
     * Gets the value of the testunitno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTestunitno() {
        return testunitno;
    }

    /**
     * Sets the value of the testunitno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTestunitno(Integer value) {
        this.testunitno = value;
    }

    /**
     * Gets the value of the upperlimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUpperlimit() {
        return upperlimit;
    }

    /**
     * Sets the value of the upperlimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUpperlimit(Integer value) {
        this.upperlimit = value;
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
     * Gets the value of the windowname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWindowname() {
        return windowname;
    }

    /**
     * Sets the value of the windowname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWindowname(String value) {
        this.windowname = value;
    }

    /**
     * Gets the value of the windowno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWindowno() {
        return windowno;
    }

    /**
     * Sets the value of the windowno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWindowno(Integer value) {
        this.windowno = value;
    }

}
