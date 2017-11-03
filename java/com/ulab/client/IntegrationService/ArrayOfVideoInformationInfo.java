
package com.ulab.client.IntegrationService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVideoInformationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVideoInformationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VideoInformationInfo" type="{http://bean}VideoInformationInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVideoInformationInfo", namespace = "http://bean", propOrder = {
    "videoInformationInfo"
})
public class ArrayOfVideoInformationInfo {

    @XmlElement(name = "VideoInformationInfo", nillable = true)
    protected List<VideoInformationInfo> videoInformationInfo;

    /**
     * Gets the value of the videoInformationInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the videoInformationInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVideoInformationInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VideoInformationInfo }
     * 
     * 
     */
    public List<VideoInformationInfo> getVideoInformationInfo() {
        if (videoInformationInfo == null) {
            videoInformationInfo = new ArrayList<VideoInformationInfo>();
        }
        return this.videoInformationInfo;
    }

}
