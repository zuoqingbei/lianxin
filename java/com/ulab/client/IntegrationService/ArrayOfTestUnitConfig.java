
package com.ulab.client.IntegrationService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfTestUnitConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfTestUnitConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TestUnitConfig" type="{http://bean}TestUnitConfig" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTestUnitConfig", namespace = "http://bean", propOrder = {
    "testUnitConfig"
})
public class ArrayOfTestUnitConfig {

    @XmlElement(name = "TestUnitConfig", nillable = true)
    protected List<TestUnitConfig> testUnitConfig;

    /**
     * Gets the value of the testUnitConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the testUnitConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTestUnitConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TestUnitConfig }
     * 
     * 
     */
    public List<TestUnitConfig> getTestUnitConfig() {
        if (testUnitConfig == null) {
            testUnitConfig = new ArrayList<TestUnitConfig>();
        }
        return this.testUnitConfig;
    }

}
