
package com.ulab.client.IntegrationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Department complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Department">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="departType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="localWebServiceList" type="{http://bean}ArrayOfLocalWebServiceInfo" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Department", namespace = "http://bean", propOrder = {
    "departType",
    "id",
    "localWebServiceList",
    "name",
    "parentId"
})
public class Department {

    @XmlElement(nillable = true)
    protected String departType;
    protected Integer id;
    @XmlElement(nillable = true)
    protected ArrayOfLocalWebServiceInfo localWebServiceList;
    @XmlElement(nillable = true)
    protected String name;
    protected Integer parentId;

    /**
     * Gets the value of the departType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartType() {
        return departType;
    }

    /**
     * Sets the value of the departType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartType(String value) {
        this.departType = value;
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
     * Gets the value of the localWebServiceList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLocalWebServiceInfo }
     *     
     */
    public ArrayOfLocalWebServiceInfo getLocalWebServiceList() {
        return localWebServiceList;
    }

    /**
     * Sets the value of the localWebServiceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLocalWebServiceInfo }
     *     
     */
    public void setLocalWebServiceList(ArrayOfLocalWebServiceInfo value) {
        this.localWebServiceList = value;
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
     * Gets the value of the parentId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the parentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParentId(Integer value) {
        this.parentId = value;
    }

}
