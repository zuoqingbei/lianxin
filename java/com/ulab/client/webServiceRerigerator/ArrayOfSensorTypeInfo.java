
package com.ulab.client.webServiceRerigerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSensorTypeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSensorTypeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SensorTypeInfo" type="{http://bean}SensorTypeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSensorTypeInfo", namespace = "http://bean", propOrder = {
    "sensorTypeInfo"
})
public class ArrayOfSensorTypeInfo {

    @XmlElement(name = "SensorTypeInfo", nillable = true)
    protected List<SensorTypeInfo> sensorTypeInfo;

    /**
     * Gets the value of the sensorTypeInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sensorTypeInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSensorTypeInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SensorTypeInfo }
     * 
     * 
     */
    public List<SensorTypeInfo> getSensorTypeInfo() {
        if (sensorTypeInfo == null) {
            sensorTypeInfo = new ArrayList<SensorTypeInfo>();
        }
        return this.sensorTypeInfo;
    }

}
