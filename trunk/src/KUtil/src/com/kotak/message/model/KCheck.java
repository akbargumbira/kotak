package com.kotak.message.model;

/**
 *
 * @author user
 */
public class KCheck extends KMessage {
    protected int clientLastRevision;

    public KCheck(String email, String pass, int clientLastRevision) {
        super(email, pass);
        this.clientLastRevision = clientLastRevision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(", clientLastRevision:")
                .append(clientLastRevision);
        
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
}
