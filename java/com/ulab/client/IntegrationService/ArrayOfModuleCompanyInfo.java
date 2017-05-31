
package com.ulab.client.IntegrationService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfModuleCompanyInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfModuleCompanyInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ModuleCompanyInfo" type="{http://bean}ModuleCompanyInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfModuleCompanyInfo", namespace = "http://bean", propOrder = {
    "moduleCompanyInfo"
})
public class ArrayOfModuleCompanyInfo {

    @XmlElement(name = "ModuleCompanyInfo", nillable = true)
    protected List<ModuleCompanyInfo> moduleCompanyInfo;

    /**
     * Gets the value of the moduleCompanyInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the moduleCompanyInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModuleCompanyInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModuleCompanyInfo }
     * 
     * 
     */
    public List<ModuleCompanyInfo> getModuleCompanyInfo() {
        if (moduleCompanyInfo == null) {
            moduleCompanyInfo = new ArrayList<ModuleCompanyInfo>();
        }
        return this.moduleCompanyInfo;
    }

}
