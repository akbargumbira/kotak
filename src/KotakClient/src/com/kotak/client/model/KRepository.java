/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kotak.client.model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class KRepository implements Serializable {
    private String name;
    private int revision;

    public KRepository() {
    }

    public KRepository(String name, int revision) {
        this.name = name;
        this.revision = revision;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the revision
     */
    public int getRevision() {
        return revision;
    }

    /**
     * @param revision the revision to set
     */
    public void setRevision(int revision) {
        this.revision = revision;
    }
}
