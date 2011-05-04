package com.kotak.message.model;

/**
 *
 * @author user
 */
public class KCheck extends KMessage {
    protected int clientLastRevision;

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
