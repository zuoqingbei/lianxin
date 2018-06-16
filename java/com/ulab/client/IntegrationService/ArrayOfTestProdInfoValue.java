
package com.ulab.client.IntegrationService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfTestProdInfoValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfTestProdInfoValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TestProdInfoValue" type="{http://bean}TestProdInfoValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTestProdInfoValue", namespace = "http://bean", propOrder = {
    "testProdInfoValue"
})
public class ArrayOfTestProdInfoValue {

    @XmlElement(name = "TestProdInfoValue", nillable = true)
    protected List<TestProdInfoValue> testProdInfoValue;

    /**
     * Gets the value of the testProdInfoValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the testProdInfoValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTestProdInfoValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TestProdInfoValue }
     * 
     * 
     */
    public List<TestProdInfoValue> getTestProdInfoValue() {
        if (testProdInfoValue == null) {
            testProdInfoValue = new ArrayList<TestProdInfoValue>();
        }
        return this.testProdInfoValue;
    }

}
