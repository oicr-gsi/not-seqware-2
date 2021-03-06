package net.sourceforge.seqware.common.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * SequencerRunAttribute generated by Morgan Taschuk
 *
 * @author boconnor
 * @version $Id: $Id
 */
public class SequencerRunAttribute extends Attribute<SequencerRun, SequencerRunAttribute> implements Serializable {

    private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sequencerRunAttributeId;
  private SequencerRun sequencerRun;
    private String tag;
    private String value;
    private String units;

    /**
     * <p>
     * Constructor for SequencerRunAttribute.
     * </p>
     */
    public SequencerRunAttribute() {
    }

    /**
     * <p>
     * Constructor for SequencerRunAttribute.
     * </p>
     *
     * @param sequencerRunAttributeId
     *            a int.
     * @param sequencerRun
     *            a {@link net.sourceforge.seqware.common.model.SequencerRun} object.
     */
  public SequencerRunAttribute(int sequencerRunAttributeId, SequencerRun sequencerRun) {
        this.sequencerRunAttributeId = sequencerRunAttributeId;
        this.sequencerRun = sequencerRun;
    }

    /**
     * <p>
     * Constructor for SequencerRunAttribute.
     * </p>
     *
     * @param sequencerRunAttributeId
     *            a int.
     * @param sequencerRun
     *            a {@link net.sourceforge.seqware.common.model.SequencerRun} object.
     * @param tag
     *            a {@link java.lang.String} object.
     * @param value
     *            a {@link java.lang.String} object.
     * @param units
     *            a {@link java.lang.String} object.
     */
  public SequencerRunAttribute(int sequencerRunAttributeId, SequencerRun sequencerRun, String tag, String value, String units) {
        this.sequencerRunAttributeId = sequencerRunAttributeId;
        this.sequencerRun = sequencerRun;
        this.tag = tag;
        this.value = value;
        this.units = units;
    }

    /**
     * <p>
     * Getter for the field <code>sequencerRunAttributeId</code>.
     * </p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getSequencerRunAttributeId() {
        return this.sequencerRunAttributeId;
    }

    /**
     * <p>
     * Setter for the field <code>sequencerRunAttributeId</code>.
     * </p>
     *
     * @param sequencerRunAttributeId
     *            a {@link java.lang.Integer} object.
     */
    public void setSequencerRunAttributeId(Integer sequencerRunAttributeId) {
        this.sequencerRunAttributeId = sequencerRunAttributeId;
    }

    /**
     * <p>
     * getSequencerRun.
     * </p>
     *
     * @return a {@link net.sourceforge.seqware.common.model.SequencerRun} object.
     */
    public SequencerRun getSequencerRun() {
        return this.sequencerRun;
    }

    /**
     * <p>
     * setSequencerRun.
     * </p>
     *
     * @param sequencerRun
     *            a {@link net.sourceforge.seqware.common.model.SequencerRun} object.
     */
  public void setSequencerRun(SequencerRun sequencerRun) {
        this.sequencerRun = sequencerRun;
    }

    /**
     * <p>
     * Getter for the field <code>tag</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
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
     * <p>
     * Getter for the field <code>value</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
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
        return "SequencerRunAttribute{" + "sequencerRunAttributeId=" + sequencerRunAttributeId + ", tag=" + tag + ", value=" + value
                + ", units=" + units + '}';
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

    /**
     * {@inheritDoc}
     *
     * @param t
     * @return
     */
    @Override
    public int compareTo(SequencerRunAttribute t) {
        return this.tag.compareTo(t.tag);
    }

    @Override
    public void setAttributeParent(SequencerRun parent) {
        this.setSequencerRun(parent);
    }

}
