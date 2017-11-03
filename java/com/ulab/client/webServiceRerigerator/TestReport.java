
package com.ulab.client.webServiceRerigerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestReport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="begintime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conclusion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filecontent" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="labcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primarykey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportinterval" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="samplinginterval" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="testtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestReport", namespace = "http://bean", propOrder = {
    "begintime",
    "conclusion",
    "endtime",
    "filecontent",
    "labcode",
    "primarykey",
    "remarks",
    "reportinterval",
    "samplinginterval",
    "testtype",
    "version"
})
public class TestReport {

    @XmlElement(nillable = true)
    protected String begintime;
    @XmlElement(nillable = true)
    protected String conclusion;
    @XmlElement(nillable = true)
    protected String endtime;
    @XmlElement(nillable = true)
    protected byte[] filecontent;
    @XmlElement(nillable = true)
    protected String labcode;
    @XmlElement(nillable = true)
    protected String primarykey;
    @XmlElement(nillable = true)
    protected String remarks;
    protected Float reportinterval;
    protected Float samplinginterval;
    @XmlElement(nillable = true)
    protected String testtype;
    @XmlElement(nillable = true)
    protected String version;

    /**
     * Gets the value of the begintime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBegintime() {
        return begintime;
    }

    /**
     * Sets the value of the begintime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBegintime(String value) {
        this.begintime = value;
    }

    /**
     * Gets the value of the conclusion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConclusion() {
        return conclusion;
    }

    /**
     * Sets the value of the conclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConclusion(String value) {
        this.conclusion = value;
    }

    /**
     * Gets the value of the endtime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * Sets the value of the endtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndtime(String value) {
        this.endtime = value;
    }

    /**
     * Gets the value of the filecontent property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFilecontent() {
        return filecontent;
    }

    /**
     * Sets the value of the filecontent property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFilecontent(byte[] value) {
        this.filecontent = value;
    }

    /**
     * Gets the value of the labcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabcode() {
        return labcode;
    }

    /**
     * Sets the value of the labcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabcode(String value) {
        this.labcode = value;
    }

    /**
     * Gets the value of the primarykey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimarykey() {
        return primarykey;
    }

    /**
     * Sets the value of the primarykey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimarykey(String value) {
        this.primarykey = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the reportinterval property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getReportinterval() {
        return reportinterval;
    }

    /**
     * Sets the value of the reportinterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setReportinterval(Float value) {
        this.reportinterval = value;
    }

    /**
     * Gets the value of the samplinginterval property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getSamplinginterval() {
        return samplinginterval;
    }

    /**
     * Sets the value of the samplinginterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setSamplinginterval(Float value) {
        this.samplinginterval = value;
    }

    /**
     * Gets the value of the testtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTesttype() {
        return testtype;
    }

    /**
     * Sets the value of the testtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTesttype(String value) {
        this.testtype = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
