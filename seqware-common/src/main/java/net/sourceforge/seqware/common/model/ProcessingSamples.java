package net.sourceforge.seqware.common.model;

/**
 * ProcessingSamples generated by hbm2java
 * 
 * @author boconnor
 * @version $Id: $Id
 */
public class ProcessingSamples implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private int processingSamplesId;
    private Processing processing;
    private Sample sample;
    private String description;
    private String label;
    private String url;
    private Integer swAccession;

    /**
     * <p>
     * Constructor for ProcessingSamples.
     * </p>
     */
    public ProcessingSamples() {
    }

    /**
     * <p>
     * Constructor for ProcessingSamples.
     * </p>
     * 
     * @param processingSamplesId
     *            a int.
     * @param processing
     *            a {@link net.sourceforge.seqware.common.model.Processing} object.
     * @param sample
     *            a {@link net.sourceforge.seqware.common.model.Sample} object.
     */
    public ProcessingSamples(int processingSamplesId, Processing processing, Sample sample) {
        this.processingSamplesId = processingSamplesId;
        this.processing = processing;
        this.sample = sample;
    }

    /**
     * <p>
     * Constructor for ProcessingSamples.
     * </p>
     * 
     * @param processingSamplesId
     *            a int.
     * @param processing
     *            a {@link net.sourceforge.seqware.common.model.Processing} object.
     * @param sample
     *            a {@link net.sourceforge.seqware.common.model.Sample} object.
     * @param description
     *            a {@link java.lang.String} object.
     * @param label
     *            a {@link java.lang.String} object.
     * @param url
     *            a {@link java.lang.String} object.
     * @param swAccession
     *            a {@link java.lang.Integer} object.
     */
    public ProcessingSamples(int processingSamplesId, Processing processing, Sample sample, String description, String label, String url,
            Integer swAccession) {
        this.processingSamplesId = processingSamplesId;
        this.processing = processing;
        this.sample = sample;
        this.description = description;
        this.label = label;
        this.url = url;
        this.swAccession = swAccession;
    }

    /**
     * <p>
     * Getter for the field <code>processingSamplesId</code>.
     * </p>
     * 
     * @return a int.
     */
    public int getProcessingSamplesId() {
        return this.processingSamplesId;
    }

    /**
     * <p>
     * Setter for the field <code>processingSamplesId</code>.
     * </p>
     * 
     * @param processingSamplesId
     *            a int.
     */
    public void setProcessingSamplesId(int processingSamplesId) {
        this.processingSamplesId = processingSamplesId;
    }

    /**
     * <p>
     * Getter for the field <code>processing</code>.
     * </p>
     * 
     * @return a {@link net.sourceforge.seqware.common.model.Processing} object.
     */
    public Processing getProcessing() {
        return this.processing;
    }

    /**
     * <p>
     * Setter for the field <code>processing</code>.
     * </p>
     * 
     * @param processing
     *            a {@link net.sourceforge.seqware.common.model.Processing} object.
     */
    public void setProcessing(Processing processing) {
        this.processing = processing;
    }

    /**
     * <p>
     * Getter for the field <code>sample</code>.
     * </p>
     * 
     * @return a {@link net.sourceforge.seqware.common.model.Sample} object.
     */
    public Sample getSample() {
        return this.sample;
    }

    /**
     * <p>
     * Setter for the field <code>sample</code>.
     * </p>
     * 
     * @param sample
     *            a {@link net.sourceforge.seqware.common.model.Sample} object.
     */
    public void setSample(Sample sample) {
        this.sample = sample;
    }

    /**
     * <p>
     * Getter for the field <code>description</code>.
     * </p>
     * 
     * @return a {@link java.lang.String} object.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Setter for the field <code>description</code>.
     * </p>
     * 
     * @param description
     *            a {@link java.lang.String} object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Getter for the field <code>label</code>.
     * </p>
     * 
     * @return a {@link java.lang.String} object.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * <p>
     * Setter for the field <code>label</code>.
     * </p>
     * 
     * @param label
     *            a {@link java.lang.String} object.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * <p>
     * Getter for the field <code>url</code>.
     * </p>
     * 
     * @return a {@link java.lang.String} object.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * <p>
     * Setter for the field <code>url</code>.
     * </p>
     * 
     * @param url
     *            a {@link java.lang.String} object.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>
     * Getter for the field <code>swAccession</code>.
     * </p>
     * 
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getSwAccession() {
        return this.swAccession;
    }

    /**
     * <p>
     * Setter for the field <code>swAccession</code>.
     * </p>
     * 
     * @param swAccession
     *            a {@link java.lang.Integer} object.
     */
    public void setSwAccession(Integer swAccession) {
        this.swAccession = swAccession;
    }

}
