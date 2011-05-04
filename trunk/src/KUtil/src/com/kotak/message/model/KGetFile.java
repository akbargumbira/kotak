package com.kotak.message.model;

/**
 *
 * @author user
 */
public class KGetFile extends KMessage {
    protected String filePath;
    protected int fileRevision;

    public KGetFile(String email, String pass, String repository, String filePath, int fileRevision) {
        super(email, pass, repository);
        this.filePath = filePath;
        this.fileRevision = fileRevision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(", fileRevision:")
                .append(fileRevision).append(", filePath:").append(filePath);
        
        return sb.toString();
    }
    
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
    public int getFileRevision() {
        return fileRevision;
    }

    /**
     * @param fileRevision the fileRevision to set
     */
    public void setFileRevision(int fileRevision) {
        this.fileRevision = fileRevision;
    }
}
