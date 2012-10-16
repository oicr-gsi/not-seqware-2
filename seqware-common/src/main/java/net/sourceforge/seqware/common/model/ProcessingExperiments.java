package net.sourceforge.seqware.common.model;

//default package
//Generated 09.12.2011 15:01:20 by Hibernate Tools 3.2.4.GA

/**
 * ProcessingExperiments generated by hbm2java
 *
 * @author boconnor
 * @version $Id: $Id
 */
public class ProcessingExperiments implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  private int processingExperimentsId;
  private Processing processing;
  private Experiment experiment;
  private String description;
  private String label;
  private String url;
  private Integer swAccession;

  /**
   * <p>Constructor for ProcessingExperiments.</p>
   */
  public ProcessingExperiments() {
  }

  /**
   * <p>Constructor for ProcessingExperiments.</p>
   *
   * @param processingExperimentsId a int.
   * @param processing a {@link net.sourceforge.seqware.common.model.Processing} object.
   * @param experiment a {@link net.sourceforge.seqware.common.model.Experiment} object.
   */
  public ProcessingExperiments(int processingExperimentsId, Processing processing, Experiment experiment) {
    this.processingExperimentsId = processingExperimentsId;
    this.processing = processing;
    this.experiment = experiment;
  }

  /**
   * <p>Constructor for ProcessingExperiments.</p>
   *
   * @param processingExperimentsId a int.
   * @param processing a {@link net.sourceforge.seqware.common.model.Processing} object.
   * @param experiment a {@link net.sourceforge.seqware.common.model.Experiment} object.
   * @param description a {@link java.lang.String} object.
   * @param label a {@link java.lang.String} object.
   * @param url a {@link java.lang.String} object.
   * @param swAccession a {@link java.lang.Integer} object.
   */
  public ProcessingExperiments(int processingExperimentsId, Processing processing, Experiment experiment,
      String description, String label, String url, Integer swAccession) {
    this.processingExperimentsId = processingExperimentsId;
    this.processing = processing;
    this.experiment = experiment;
    this.description = description;
    this.label = label;
    this.url = url;
    this.swAccession = swAccession;
  }

  /**
   * <p>Getter for the field <code>processingExperimentsId</code>.</p>
   *
   * @return a int.
   */
  public int getProcessingExperimentsId() {
    return this.processingExperimentsId;
  }

  /**
   * <p>Setter for the field <code>processingExperimentsId</code>.</p>
   *
   * @param processingExperimentsId a int.
   */
  public void setProcessingExperimentsId(int processingExperimentsId) {
    this.processingExperimentsId = processingExperimentsId;
  }

  /**
   * <p>Getter for the field <code>processing</code>.</p>
   *
   * @return a {@link net.sourceforge.seqware.common.model.Processing} object.
   */
  public Processing getProcessing() {
    return this.processing;
  }

  /**
   * <p>Setter for the field <code>processing</code>.</p>
   *
   * @param processing a {@link net.sourceforge.seqware.common.model.Processing} object.
   */
  public void setProcessing(Processing processing) {
    this.processing = processing;
  }

  /**
   * <p>Getter for the field <code>experiment</code>.</p>
   *
   * @return a {@link net.sourceforge.seqware.common.model.Experiment} object.
   */
  public Experiment getExperiment() {
    return this.experiment;
  }

  /**
   * <p>Setter for the field <code>experiment</code>.</p>
   *
   * @param experiment a {@link net.sourceforge.seqware.common.model.Experiment} object.
   */
  public void setExperiment(Experiment experiment) {
    this.experiment = experiment;
  }

  /**
   * <p>Getter for the field <code>description</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * <p>Setter for the field <code>description</code>.</p>
   *
   * @param description a {@link java.lang.String} object.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * <p>Getter for the field <code>label</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getLabel() {
    return this.label;
  }

  /**
   * <p>Setter for the field <code>label</code>.</p>
   *
   * @param label a {@link java.lang.String} object.
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * <p>Getter for the field <code>url</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * <p>Setter for the field <code>url</code>.</p>
   *
   * @param url a {@link java.lang.String} object.
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * <p>Getter for the field <code>swAccession</code>.</p>
   *
   * @return a {@link java.lang.Integer} object.
   */
  public Integer getSwAccession() {
    return this.swAccession;
  }

  /**
   * <p>Setter for the field <code>swAccession</code>.</p>
   *
   * @param swAccession a {@link java.lang.Integer} object.
   */
  public void setSwAccession(Integer swAccession) {
    this.swAccession = swAccession;
  }

}