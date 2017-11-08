
package com.ulab.client.webServiceRerigerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfNozzleList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfNozzleList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NozzleList" type="{http://bean}NozzleList" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfNozzleList", namespace = "http://bean", propOrder = {
    "nozzleList"
})
public class ArrayOfNozzleList {

    @XmlElement(name = "NozzleList", nillable = true)
    protected List<NozzleList> nozzleList;

    /**
     * Gets the value of the nozzleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nozzleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNozzleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NozzleList }
     * 
     * 
     */
    public List<NozzleList> getNozzleList() {
        if (nozzleList == null) {
            nozzleList = new ArrayList<NozzleList>();
        }
        return this.nozzleList;
    }

}
