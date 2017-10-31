
package com.ulab.client.webServiceRerigerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGroupConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGroupConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroupConfig" type="{http://bean}GroupConfig" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGroupConfig", namespace = "http://bean", propOrder = {
    "groupConfig"
})
public class ArrayOfGroupConfig {

    @XmlElement(name = "GroupConfig", nillable = true)
    protected List<GroupConfig> groupConfig;

    /**
     * Gets the value of the groupConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroupConfig }
     * 
     * 
     */
    public List<GroupConfig> getGroupConfig() {
        if (groupConfig == null) {
            groupConfig = new ArrayList<GroupConfig>();
        }
        return this.groupConfig;
    }

}
