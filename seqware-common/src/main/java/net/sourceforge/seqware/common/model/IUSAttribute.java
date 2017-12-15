package net.sourceforge.seqware.common.model;

//default package

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Generated 09.12.2011 15:01:20 by Hibernate Tools 3.2.4.GA

/**
 * IusAttribute generated by hbm2java
 *
 * @author boconnor
 * @version $Id: $Id
 */
public class IUSAttribute extends Attribute<IUS, IUSAttribute> implements Serializable {

    private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iusAttributeId;
    private IUS ius;
    private String tag;
    private String value;
    private String units;

    /**
     * <p>
     * Constructor for IUSAttribute.
     * </p>
     */
    public IUSAttribute() {
    }

    /**
     * <p>
     * Constructor for IUSAttribute.
     * </p>
     *
     * @param iusAttributeId
     *            a int.
     * @param ius
     *            a {@link net.sourceforge.seqware.common.model.IUS} object.
     */
    public IUSAttribute(int iusAttributeId, IUS ius) {
        this.iusAttributeId = iusAttributeId;
        this.ius = ius;
    }

    /**
     * <p>
     * Constructor for IUSAttribute.
     * </p>
     *
     * @param iusAttributeId
     *            a int.
     * @param ius
     *            a {@link net.sourceforge.seqware.common.model.IUS} object.
     * @param tag
     *            a {@link java.lang.String} object.
     * @param value
     *            a {@link java.lang.String} object.
     * @param units
     *            a {@link java.lang.String} object.
     */
    public IUSAttribute(int iusAttributeId, IUS ius, String tag, String value, String units) {
        this.iusAttributeId = iusAttributeId;
        this.ius = ius;
        this.tag = tag;
        this.value = value;
        this.units = units;
    }

    /**
     * <p>
     * Getter for the field <code>iusAttributeId</code>.
     * </p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getIusAttributeId() {
        return this.iusAttributeId;
    }

    /**
     * <p>
     * Setter for the field <code>iusAttributeId</code>.
     * </p>
     *
     * @param iusAttributeId
     *            a {@link java.lang.Integer} object.
     */
    public void setIusAttributeId(Integer iusAttributeId) {
        this.iusAttributeId = iusAttributeId;
    }

    /**
     * <p>
     * Getter for the field <code>ius</code>.
     * </p>
     *
     * @return a {@link net.sourceforge.seqware.common.model.IUS} object.
     */
    public IUS getIus() {
        return this.ius;
    }

    /**
     * <p>
     * Setter for the field <code>ius</code>.
     * </p>
     *
     * @param ius
     *            a {@link net.sourceforge.seqware.common.model.IUS} object.
     */
    public void setIus(IUS ius) {
        this.ius = ius;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getTag() {
        return this.tag;
    }

    /** {@inheritDoc} */
    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>
     * Getter for the field <code>units</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUnits() {
        return this.units;
    }

    /**
     * <p>
     * Setter for the field <code>units</code>.
     * </p>
     *
     * @param units
     *            a {@link java.lang.String} object.
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "IUSAttribute{" + "iusAttributeId=" + iusAttributeId + ", tag=" + tag + ", value=" + value + ", units=" + units + '}';
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getUnit() {
        return this.getUnits();
    }

    /** {@inheritDoc} */
    @Override
    public void setUnit(String unit) {
        this.setUnits(unit);
    }

    @Override
    public void setAttributeParent(IUS parent) {
        this.setIus(parent);
    }

}
