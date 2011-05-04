package com.kotak.message.model;

/**
 *
 * @author user
 */
public class KMessage {
    protected String email;
    protected String pass;
    protected String repository;

    public KMessage(String email, String pass, String repository) {
        this.email = email;
        this.pass = pass;
        this.repository = repository;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("email:").append(email).append(", pass:").append(pass)
                .append(", repository:").append(repository);
        
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

    /**
     * @return the repository
     */
    public String getRepository() {
        return repository;
    }

    /**
     * @param repository the repository to set
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }
    
    
}