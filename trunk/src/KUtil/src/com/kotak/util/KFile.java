package com.kotak.util;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class KFile {

    public static void main(String[] args) {
        String tes = "ha/hi/hhu";
        String[] part = tes.split("/", 2);
        System.out.println(part.length);
        System.out.println(part[0]);
        System.out.println(part[1]);
        KFile root = new KFile("root", new Date());
        KFile temp;
        root.addFile(new KFile("a.txt", new Date()));
        temp = new KFile("folder 1", new Date());
        temp.addFile(new KFile("b.txt", new Date()));
        temp.addFile(new KFile("folder 2", new Date()));
        root.addFile(temp);

        System.out.println(root.findFile("folder 1folder 2"));
    }
    
    private String name;
    private Date modified;
    private KFile parent;
    
    /**
     * This object is a folder if size of <i>files</i> > 0
     */
    private ArrayList<KFile> files = new ArrayList<KFile>();

    public KFile(String name, Date modified) {
        this.name = name;
        this.modified = modified;
        this.parent = null;
    }

    /**
     * Compare : may be not used :( failed
     * @param kFile
     * @return 
     */
    public boolean compare(KFile kFile) {
        if (kFile == null) {
            return false;
        }

        if (name.equals(kFile.getName())) {
            // Name are equal

            if (files.size() != getFiles().size()) {
                // files size is not equal
                return false;
            }

            if (files.isEmpty()) {
                // This is a file
                return true;
            }

            // This is a folder
            int size = files.size();
            for (int i = 0; i < size; ++i) {
                if (!files.get(i).compare(kFile.getFiles().get(i))) {
                    // Not equal
                    return false;
                }
            }

            return true;
        }

        // Name not equal
        return false;
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

        // Find
        for (int i = 0; i < size; ++i) {
            if (files.get(i).getName().equals(part[0])) {
                if (part.length == 1) {
                    return files.get(i);
                }
                
                return files.get(i).findFile(part[1]);
            }
        }

        return null;
    }
    
    /**
     * Remove file or folder
     * @param path Path of file or folder
     * @return True if success. False if not success
     */
    public boolean removeFile(String path) {
        // TODO test
        KFile file = findFile(path);
        
        if(file == null) {
            return false;
        }
        
        file.getParent().getFiles().remove(file);
        
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

    public void addFile(KFile file) {
        getFiles().add(file);
        file.setParent(this);
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
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return the files
     */
    public ArrayList<KFile> getFiles() {
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
        return new Gson().fromJson(json, KFile.class);
    }
    
    /**
     * To JSON
     * @param kFile Object will be converted to string JSON
     * @return JSON string of <i>kFile</i>
     */
    public static String toJSON(KFile kFile) {
        return new Gson().toJson(kFile);
    }

    /**
     * @return the parent
     */
    public KFile getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(KFile parent) {
        this.parent = parent;
    }
}