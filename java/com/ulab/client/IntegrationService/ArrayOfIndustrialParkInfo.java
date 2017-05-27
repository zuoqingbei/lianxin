
package com.ulab.client.IntegrationService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfIndustrialParkInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfIndustrialParkInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IndustrialParkInfo" type="{http://bean}IndustrialParkInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfIndustrialParkInfo", namespace = "http://bean", propOrder = {
    "industrialParkInfo"
})
public class ArrayOfIndustrialParkInfo {

    @XmlElement(name = "IndustrialParkInfo", nillable = true)
    protected List<IndustrialParkInfo> industrialParkInfo;

    /**
     * Gets the value of the industrialParkInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the industrialParkInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndustrialParkInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndustrialParkInfo }
     * 
     * 
     */
    public List<IndustrialParkInfo> getIndustrialParkInfo() {
        if (industrialParkInfo == null) {
            industrialParkInfo = new ArrayList<IndustrialParkInfo>();
        }
        return this.industrialParkInfo;
    }

}
