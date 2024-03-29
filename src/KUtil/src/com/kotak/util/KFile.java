package com.kotak.util;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KFile extends KFileJSON {
    
    private KFile parent;

    public KFile(String name, Date modified, boolean isFile) {
        this.name = name;
        this.lastModified = modified;
        this.isFile = isFile;
        this.parent = null;
    }

    /**
     * Find file or folder
     * @param path Path of file or folder to be search
     * @return Corespondent object if found. Null if not found
     * 
     * Path example : progin5/tes/Main.java
     */
    public KFile findFile(String path) {
        // Clean whitespace
        path = path.trim();
        
        // Split
        String[] part = path.split("//*", 2);
        
        // Get size
        int size = files.size();
        
        // Temp
        KFile temp;

        // Find
        for (int i = 0; i < size; ++i) {
            temp = (KFile) files.get(i);
            if (temp.getName().equals(part[0])) {
                if (part.length == 1) {
                    return temp;
                }
                
                return temp.findFile(part[1]);
            }
        }

        return null;
    }
    
    /**
     * Remove file or folder
     * @param path Path of file or folder
     * @param removeTime Parent last modified
     * @return True if success. False if not success
     */
    public boolean removeFile(String path, Date removeTime) {
        // TODO test
        KFile file = findFile(path);
        
        if(file == null) {
            return false;
        }
        
        // Remove
        file.getParent().getFiles().remove(file);
        
        // Modified recursive
        file.parent.modifiedRecursive(removeTime);
        
        return true;
    }
    
    /**
     * Check if path is exist
     * @param path The path
     * @return True if exist. False if not exist
     */
    public boolean isExist(String path) {
        return findFile(path) != null;
    }

    // TODO addTime
    public void addFile(KFile file) {
        // Add to files container
        getFiles().add(file);
        
        // Set Parent
        file.parent = this;
        
        // Modified Recursive
        file.modifiedRecursive(file.lastModified);
    }
    
    public void mkdirs(String path, Date date) {
        String[] part = path.split("/", 2);
        KFile find = findFile(part[0]);
        if (find != null) {
            find.lastModified = date;
        } else {
            KFile newFile = new KFile(part[0], date, false);
            addFile(newFile);
            find = newFile;
        }
        
        if (part.length == 2) {
            find.mkdirs(part[1], date);
        }
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
     * @return the modified
     */
    public Date getModified() {
        return lastModified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        modifiedRecursive(modified);
    }

    /**
     * @return the files
     */
    public LinkedList<KFileJSON> getFiles() {
        return files;
    }
    
    /**
     * Load JSON from file and create KFile object
     * @param jsonPath Path of JSON file
     * @return KFile object. Null if jsonPath not found or there are something error
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static KFile fromJSONFile(String jsonPath) throws FileNotFoundException, IOException {
        File file = new File(jsonPath);
        
        if (!file.exists()) {
            // Path is not exist
            return null;
        }
        
        // Read file content
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fis.read(bytes);
        
        // Load from JSON
        return fromJSONString(new String(bytes));
    }
    
    /**
     * Load KFile object from JSON string
     * @param json String of JSON
     * @return  KFile object. Null if there are something error
     */
    public static KFile fromJSONString(String json) {
        KFileJSON jsonFile = new Gson().fromJson(json, KFileJSON.class);
        return toKFile(jsonFile);
    }
    
    /**
     * 
     * @param fileJSON
     * @return 
     */
    public static KFile toKFile(KFileJSON fileJSON) {
        KFile file = new KFile(fileJSON.name, fileJSON.lastModified, fileJSON.isFile);
        LinkedList<KFileJSON> files = fileJSON.files;
        
        for (KFileJSON temp : files) {
            file.addFile(toKFile(temp));
        }
        
        return file;
    }
    
    /**
     * To JSON
     * @param kFile Object will be converted to string JSON
     * @return JSON string of <i>kFile</i>
     */
    public static String toJSON(KFile kFile) {
        return new Gson().toJson((KFileJSON)kFile);
    }
    
    public String toJSON() {
        return toJSON(this);
    }

    /**
     * @return the parent
     */
    public KFile getParent() {
        return parent;
    }
    
    private void modifiedRecursive(Date date) {
        lastModified = date;
        
        if (parent == null) {
            return;
        }
        
        parent.modifiedRecursive(date);
    }
    
    public boolean isFile() {
        return isFile;
    }
    
    public boolean isDir() {
        return !isFile;
    }
    
    public static String getParentPath(String path) {
        String parentPath = path.replaceAll("//*[^/]*$", "");     
        
        if(parentPath == null ? path == null : parentPath.equals(path)) {
            return null;
        }
        
        return parentPath;
    }
    
    public static void main(String[] args) {
        try {
            System.out.println(new Date());
            Thread.sleep(1000);
            KFile file = new KFile("rezanachmad@gmail.com", new Date(), false);
            Thread.sleep(1000);
            file.addFile(new KFile("r", new Date(), true));
            Thread.sleep(1000);
            file.mkdirs("hai/tesaja", new Date());
            Thread.sleep(1000);
            file.findFile("hai/tesaja").addFile(new KFile("he", new Date(), true));
            System.out.println(file.toJSON());
            System.out.println(file.getTree());
        } catch (InterruptedException ex) {
            Logger.getLogger(KFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}