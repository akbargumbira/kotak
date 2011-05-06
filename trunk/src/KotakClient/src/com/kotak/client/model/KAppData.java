/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kotak.client.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class KAppData implements Serializable {
    private static String fileName = ".kotak";

    /**
     * @return the instance
     */
    public static KAppData getInstance() {        
        if (instance == null) {
            try {
                load();
            } catch (FileNotFoundException ex) {
                try {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex);
                    instance = new KAppData();
                    KAppData.save();
                } catch (FileNotFoundException ex1) {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (IOException ex1) {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (IOException ex) {
                try {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex);
                    instance = new KAppData();
                    KAppData.save();
                } catch (FileNotFoundException ex1) {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (IOException ex1) {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                try {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex);
                    instance = new KAppData();
                    KAppData.save();
                } catch (FileNotFoundException ex1) {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (IOException ex1) {
                    Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(KAppData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return instance;
    }
    private String serverURL = "127.0.0.1";
    private int serverPort = 10000;
    private String workingFolderPath = "";
    protected String workingFolderName = "Kotak";
    
    private String email;
    private String password;
    
    private ArrayList<KRepository> repositories;
    private static KAppData instance;
    private boolean login = false;

    public KAppData() {
        repositories = new ArrayList<KRepository>();
        KRepository temp = new KRepository("indonesia", 1);
        repositories.add(temp);
    }

    public static KAppData load() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        instance = (KAppData)ois.readObject();
        ois.close();

        return instance;
    }

    public static void save() throws FileNotFoundException, IOException {
        if (instance != null) {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
            oos.close();
        }
    }

    /**
     * @return the serverURL
     */
    public String getServerURL() {
        return serverURL;
    }

    /**
     * @param serverURL the serverURL to set
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    /**
     * @return the serverPort
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return the workingFolder
     */
    public String getWorkingFolderPath() {
        return workingFolderPath;
    }

    /**
     * @param workingFolderPath the workingFolder to set
     */
    public void setWorkingFolderPath(String workingFolderPath) {
        this.workingFolderPath = workingFolderPath;
    }

    /**
     * @return the repositories
     */
    public ArrayList<KRepository> getRepositories() {
        return repositories;
    }

    /**
     * @param repositories the repositories to set
     */
    public void setRepositories(ArrayList<KRepository> repositories) {
        this.setRepositories(repositories);
    }

    /**
     * @return the username
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.email = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the login
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(boolean login) {
        this.login = login;
    }

    /**
     * @return the workingFolderName
     */
    public String getWorkingFolderName() {
        return workingFolderName;
    }

    /**
     * @param workingFolderName the workingFolderName to set
     */
    public void setWorkingFolderName(String workingFolderName) {
        this.workingFolderName = workingFolderName;
    }
    
    /**
     * Get repository path
     * @return Repository path
     */
    public String getRepoPath() {
        String repoPath;
        
        if ("".equals(workingFolderPath)) {
            repoPath = workingFolderName + "/" + email;
        } else {
            repoPath = workingFolderPath + "/" + workingFolderName + "/" + email;
        }
        
        return repoPath;
    }
}
