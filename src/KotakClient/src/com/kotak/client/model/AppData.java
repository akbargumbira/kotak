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

/**
 *
 * @author user
 */
public class AppData implements Serializable {
    private static String fileName = ".kotak";
    private String serverURL = "127.0.0.1";
    private int serverPort = 10000;
    private String workingFolder = "";
    private String email;
    private String password;
    private ArrayList<Repository> repositories;
    public static AppData instance;
    private boolean login = false;

    public AppData() {
        repositories = new ArrayList<Repository>();
        Repository temp = new Repository("indonesia", 1);
        repositories.add(temp);
    }

    public static AppData load() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        AppData data = (AppData)ois.readObject();
        ois.close();

        return data;
    }

    public static void save(AppData data) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(data);
        oos.close();
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
    public String getWorkingFolder() {
        return workingFolder;
    }

    /**
     * @param workingFolder the workingFolder to set
     */
    public void setWorkingFolder(String workingFolder) {
        this.workingFolder = workingFolder;
    }

    /**
     * @return the repositories
     */
    public ArrayList<Repository> getRepositories() {
        return repositories;
    }

    /**
     * @param repositories the repositories to set
     */
    public void setRepositories(ArrayList<Repository> repositories) {
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
}
