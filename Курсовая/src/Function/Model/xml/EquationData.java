//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.07 at 12:38:15 PM EET 
//


package Function.Model.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Coefs">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Coef" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                           &lt;attribute name="Index" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Points">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="XYCoef" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="X" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                           &lt;attribute name="Y" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "coefs",
    "points"
})
@XmlRootElement(name = "EquationData")
public class EquationData {

    @XmlElement(name = "Coefs", required = true)
    protected EquationData.Coefs coefs;
    @XmlElement(name = "Points", required = true)
    protected EquationData.Points points;

    /**
     * Gets the value of the coefs property.
     * 
     * @return
     *     possible object is
     *     {@link EquationData.Coefs }
     *     
     */
    public EquationData.Coefs getCoefs() {
        return coefs;
    }

    /**
     * Sets the value of the coefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquationData.Coefs }
     *     
     */
    public void setCoefs(EquationData.Coefs value) {
        this.coefs = value;
    }

    /**
     * Gets the value of the points property.
     * 
     * @return
     *     possible object is
     *     {@link EquationData.Points }
     *     
     */
    public EquationData.Points getPoints() {
        return points;
    }

    /**
     * Sets the value of the points property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquationData.Points }
     *     
     */
    public void setPoints(EquationData.Points value) {
        this.points = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Coef" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
     *                 &lt;attribute name="Index" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "coef"
    })
    public static class Coefs {

        @XmlElement(name = "Coef", required = true)
        protected List<EquationData.Coefs.Coef> coef;

        /**
         * Gets the value of the coef property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the coef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCoef().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EquationData.Coefs.Coef }
         * 
         * 
         */
        public List<EquationData.Coefs.Coef> getCoef() {
            if (coef == null) {
                coef = new ArrayList<EquationData.Coefs.Coef>();
            }
            return this.coef;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
         *       &lt;attribute name="Index" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Coef {

            @XmlAttribute(name = "Value", required = true)
            protected double value;
            @XmlAttribute(name = "Index", required = true)
            protected int index;

            /**
             * Gets the value of the value property.
             * 
             */
            public double getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             */
            public void setValue(double value) {
                this.value = value;
            }

            /**
             * Gets the value of the index property.
             * 
             */
            public int getIndex() {
                return index;
            }

            /**
             * Sets the value of the index property.
             * 
             */
            public void setIndex(int value) {
                this.index = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="XYCoef" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="X" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
     *                 &lt;attribute name="Y" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "xyCoef"
    })
    public static class Points {

        @XmlElement(name = "XYCoef", required = true)
        protected List<EquationData.Points.XYCoef> xyCoef;

        /**
         * Gets the value of the xyCoef property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the xyCoef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getXYCoef().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EquationData.Points.XYCoef }
         * 
         * 
         */
        public List<EquationData.Points.XYCoef> getXYCoef() {
            if (xyCoef == null) {
                xyCoef = new ArrayList<EquationData.Points.XYCoef>();
            }
            return this.xyCoef;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="X" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
         *       &lt;attribute name="Y" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class XYCoef {

            @XmlAttribute(name = "X", required = true)
            protected double x;
            @XmlAttribute(name = "Y", required = true)
            protected double y;

            /**
             * Gets the value of the x property.
             * 
             */
            public double getX() {
                return x;
            }

            /**
             * Sets the value of the x property.
             * 
             */
            public void setX(double value) {
                this.x = value;
            }

            /**
             * Gets the value of the y property.
             * 
             */
            public double getY() {
                return y;
            }

            /**
             * Sets the value of the y property.
             * 
             */
            public void setY(double value) {
                this.y = value;
            }

        }

    }

}
