
package com.ulab.client.webServiceRerigerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCoordinateInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCoordinateInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CoordinateInfo" type="{http://bean}CoordinateInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCoordinateInfo", namespace = "http://bean", propOrder = {
    "coordinateInfo"
})
public class ArrayOfCoordinateInfo {

    @XmlElement(name = "CoordinateInfo", nillable = true)
    protected List<CoordinateInfo> coordinateInfo;

    /**
     * Gets the value of the coordinateInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coordinateInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoordinateInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CoordinateInfo }
     * 
     * 
     */
    public List<CoordinateInfo> getCoordinateInfo() {
        if (coordinateInfo == null) {
            coordinateInfo = new ArrayList<CoordinateInfo>();
        }
        return this.coordinateInfo;
    }

}
