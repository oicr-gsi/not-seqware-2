/**
 *
 */
package com.github.seqware.queryengine.system.importers.workers;

import com.github.seqware.queryengine.system.importers.Importer;
import com.github.seqware.queryengine.util.SGID;

/**
 * Base Worker class, looks like a Bean for storing settings about the file to
 * be converted.
 *
 * @author dyuen
 * @author boconnor
 *
 */
public class ImportWorker extends Thread {

    String workerName = null;
    Importer pmi = null;
    // Actually, on second thought. I think a shared CreateUpdateManager would be a bad idea. 
    // We don't really want all threads to freeze
    //    CreateUpdateManager mManager = null;
    //Store store = null;
    String input = null;
    boolean compressed = false;
    int minCoverage;
    int maxCoverage;
    float minSnpQuality;
    boolean includeSNV;
    int fastqConvNum;
    boolean includeIndels;
    boolean includeCoverage = false;
    int binSize = 0;
    SGID featureSetID = null;

    public ImportWorker() {
    }

    public ImportWorker(String workerName, Importer pmi, // CreateUpdateManager store,
             String input, boolean compressed, int minCoverage, int maxCoverage, 
             float minSnpQuality, boolean includeSNV, int fastqConvNum, 
             boolean includeIndels, boolean includeCoverage, int binSize) {
        this.workerName = workerName;
        this.pmi = pmi;
//        this.mManager = store;
        this.input = input;
        this.compressed = compressed;
        this.minCoverage = minCoverage;
        this.maxCoverage = maxCoverage;
        this.minSnpQuality = minSnpQuality;
        this.includeSNV = includeSNV;
        this.fastqConvNum = fastqConvNum;
        this.includeIndels = includeIndels;
        this.includeCoverage = includeCoverage;
        this.binSize = binSize;
    }

    @Override
    public void run() {
    }

    // autogenerated
    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Importer getPmi() {
        return pmi;
    }

    public void setPmi(Importer pmi) {
        this.pmi = pmi;
    }

//    public CreateUpdateManager getStore() {
//        return mManager;
//        // return this.store
//    }
//
//    public void setStore(CreateUpdateManager mManager) {
//        //this.store = store
//        this.mManager = mManager;
//    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public int getMinCoverage() {
        return minCoverage;
    }

    public void setMinCoverage(int minCoverage) {
        this.minCoverage = minCoverage;
    }

    public int getMaxCoverage() {
        return maxCoverage;
    }

    public void setMaxCoverage(int maxCoverage) {
        this.maxCoverage = maxCoverage;
    }

    public float getMinSnpQuality() {
        return minSnpQuality;
    }

    public void setMinSnpQuality(float minSnpQuality) {
        this.minSnpQuality = minSnpQuality;
    }

    public boolean isIncludeSNV() {
        return includeSNV;
    }

    public void setIncludeSNV(boolean includeSNV) {
        this.includeSNV = includeSNV;
    }

    public int getFastqConvNum() {
        return fastqConvNum;
    }

    public void setFastqConvNum(int fastqConvNum) {
        this.fastqConvNum = fastqConvNum;
    }

    public boolean isIncludeIndels() {
        return includeIndels;
    }

    public void setIncludeIndels(boolean includeIndels) {
        this.includeIndels = includeIndels;
    }

    public boolean isIncludeCoverage() {
        return includeCoverage;
    }

    public void setIncludeCoverage(boolean includeCoverage) {
        this.includeCoverage = includeCoverage;
    }

    public int getBinSize() {
        return binSize;
    }

    public void setBinSize(int binSize) {
        this.binSize = binSize;
    }

    public SGID getFeatureSetID() {
        return featureSetID;
    }

    public void setFeatureSetID(SGID featureSetID) {
        this.featureSetID = featureSetID;
    }
    
    
}
