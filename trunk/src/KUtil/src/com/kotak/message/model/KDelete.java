package com.kotak.message.model;

/**
 *
 * @author user
 */
public class KDelete extends KMessage {
    protected String filePath;
    protected int clientLastRevision;

    public KDelete(String email, String pass, String filePath, int clientLastRevision) {
        super(email, pass);
        this.filePath = filePath;
        this.clientLastRevision = clientLastRevision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(", clientLastRevision:")
                .append(clientLastRevision).append(", filePath:").append(filePath);
        
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
}
