
package com.ulab.client.webServiceRerigerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfReportClass complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfReportClass">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReportClass" type="{http://bean}ReportClass" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfReportClass", namespace = "http://bean", propOrder = {
    "reportClass"
})
public class ArrayOfReportClass {

    @XmlElement(name = "ReportClass", nillable = true)
    protected List<ReportClass> reportClass;

    /**
     * Gets the value of the reportClass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportClass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportClass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportClass }
     * 
     * 
     */
    public List<ReportClass> getReportClass() {
        if (reportClass == null) {
            reportClass = new ArrayList<ReportClass>();
        }
        return this.reportClass;
    }

}
