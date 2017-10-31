
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeviceAlarm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceAlarm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alarmContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alarmTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceAlarm", namespace = "http://bean", propOrder = {
    "alarmContent",
    "alarmTime"
})
public class DeviceAlarm {

    @XmlElement(nillable = true)
    protected String alarmContent;
    @XmlElement(nillable = true)
    protected String alarmTime;

    /**
     * Gets the value of the alarmContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlarmContent() {
        return alarmContent;
    }

    /**
     * Sets the value of the alarmContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlarmContent(String value) {
        this.alarmContent = value;
    }

    /**
     * Gets the value of the alarmTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlarmTime() {
        return alarmTime;
    }

    /**
     * Sets the value of the alarmTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlarmTime(String value) {
        this.alarmTime = value;
    }

}
