package com.kotak.message.model;

/**
 *
 * @author user
 */
public class KGetFile extends KMessage {
    protected String filePath;
    protected String fileRevision;

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileRevision
     */
    public String getFileRevision() {
        return fileRevision;
    }

    /**
     * @param fileRevision the fileRevision to set
     */
    public void setFileRevision(String fileRevision) {
        this.fileRevision = fileRevision;
    }
}
