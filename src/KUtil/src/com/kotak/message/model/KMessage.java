package com.kotak.message.model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class KMessage implements Serializable {
    protected String email;
    protected String pass;

    public KMessage(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("email:").append(email).append(", pass:").append(pass);
        
        return sb.toString();
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
}