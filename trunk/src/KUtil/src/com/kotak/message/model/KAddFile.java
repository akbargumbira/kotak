package com.kotak.message.model;

/**
 *
 * @author user
 */
public class KAddFile extends KMessage {
    protected int clientLastRevision;
    protected String filePath;
    protected byte[] fileContent;

    public KAddFile(String email, String pass, int clientLastRevision, String filePath, byte[] fileContent) {
        super(email, pass);
        this.clientLastRevision = clientLastRevision;
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(", clientLastRevision:")
                .append(clientLastRevision).append(", filePath:").append(filePath);
        
        return sb.toString();
    }
    
    /**
     * @return the clientLastRevision
     */
    public int getClientLastRevision() {
        return clientLastRevision;
    }

    /**
     * @param clientLastRevision the clientLastRevision to set
     */
    public void setClientLastRevision(int clientLastRevision) {
        this.clientLastRevision = clientLastRevision;
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
     * @return the fileContent
     */
    public byte[] getFileContent() {
        return fileContent;
    }

    /**
     * @param fileContent the fileContent to set
     */
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    
}
