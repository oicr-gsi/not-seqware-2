package net.sourceforge.seqware.common.model;

import java.io.Serializable;

//Generated 09.12.2011 15:01:20 by Hibernate Tools 3.2.4.GA

/**
 * ExperimentAttribute generated by hbm2java
 *
 * @author boconnor
 * @version $Id: $Id
 */
public class ExperimentAttribute extends Attribute<Experiment, ExperimentAttribute> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int experimentAttributeId;
    private Experiment experiment;
    private String tag;
    private String value;
    private String units;

    /**
     * <p>
     * Constructor for ExperimentAttribute.
     * </p>
     */
    public ExperimentAttribute() {
    }

    /**
     * <p>
     * Constructor for ExperimentAttribute.
     * </p>
     *
     * @param experimentAttributeId
     *            a int.
     * @param experiment
     *            a {@link net.sourceforge.seqware.common.model.Experiment} object.
     */
    public ExperimentAttribute(int experimentAttributeId, Experiment experiment) {
        this.experimentAttributeId = experimentAttributeId;
        this.experiment = experiment;
    }

    /**
     * <p>
     * Constructor for ExperimentAttribute.
     * </p>
     *
     * @param experimentAttributeId
     *            a int.
     * @param experiment
     *            a {@link net.sourceforge.seqware.common.model.Experiment} object.
     * @param tag
     *            a {@link java.lang.String} object.
     * @param value
     *            a {@link java.lang.String} object.
     * @param units
     *            a {@link java.lang.String} object.
     */
    public ExperimentAttribute(int experimentAttributeId, Experiment experiment, String tag, String value, String units) {
        this.experimentAttributeId = experimentAttributeId;
        this.experiment = experiment;
        this.tag = tag;
        this.value = value;
        this.units = units;
    }

    /**
     * <p>
     * Getter for the field <code>experimentAttributeId</code>.
     * </p>
     *
     * @return a int.
     */
    public int getExperimentAttributeId() {
        return this.experimentAttributeId;
    }

    /**
     * <p>
     * Setter for the field <code>experimentAttributeId</code>.
     * </p>
     *
     * @param experimentAttributeId
     *            a int.
     */
    public void setExperimentAttributeId(int experimentAttributeId) {
        this.experimentAttributeId = experimentAttributeId;
    }

    /**
     * <p>
     * Getter for the field <code>experiment</code>.
     * </p>
     *
     * @return a {@link net.sourceforge.seqware.common.model.Experiment} object.
     */
    public Experiment getExperiment() {
        return this.experiment;
    }

    /**
     * <p>
     * Setter for the field <code>experiment</code>.
     * </p>
     *
     * @param experiment
     *            a {@link net.sourceforge.seqware.common.model.Experiment} object.
     */
    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
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

    /**
     * <p>
     * Setter for the field <code>tag</code>.
     * </p>
     *
     * @param tag
     *            a {@link java.lang.String} object.
     */
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

    /**
     * <p>
     * Setter for the field <code>value</code>.
     * </p>
     *
     * @param value
     *            a {@link java.lang.String} object.
     */
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

    @Override
    public void setAttributeParent(Experiment parent) {
        this.setExperiment(parent);
    }

    @Override
    public String getUnit() {
        // this sucks, but plural non-interface version was already exposed and looks in use
        return this.getUnits();
    }

    @Override
    public void setUnit(String unit) {
        this.setUnits(unit);
    }
}
