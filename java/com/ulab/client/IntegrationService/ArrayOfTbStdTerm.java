
package com.ulab.client.IntegrationService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfTbStdTerm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfTbStdTerm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TbStdTerm" type="{http://bean}TbStdTerm" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTbStdTerm", namespace = "http://bean", propOrder = {
    "tbStdTerm"
})
public class ArrayOfTbStdTerm {

    @XmlElement(name = "TbStdTerm", nillable = true)
    protected List<TbStdTerm> tbStdTerm;

    /**
     * Gets the value of the tbStdTerm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tbStdTerm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTbStdTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TbStdTerm }
     * 
     * 
     */
    public List<TbStdTerm> getTbStdTerm() {
        if (tbStdTerm == null) {
            tbStdTerm = new ArrayList<TbStdTerm>();
        }
        return this.tbStdTerm;
    }

}
