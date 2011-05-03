package com.kotak.util;

import java.util.ArrayList;
import java.util.Date;

public class KFile {

    public static void main(String[] args) {
        String tes = "ha/hi/hhu";
        String[] part = tes.split("/", 2);
        System.out.println(part.length);
        System.out.println(part[0]);
        System.out.println(part[1]);
        KFile root = new KFile("root", (new Date()));
        KFile temp;
        root.AddFile(new KFile("a.txt", (new Date())));
        temp = new KFile("folder 1", (new Date()));
        temp.AddFile(new KFile("b.txt", (new Date())));
        temp.AddFile(new KFile("folder 2", (new Date())));
        root.AddFile(temp);

        System.out.println(root.findFile("folder 1folder 2"));
    }
    
    private String name;
    private Date modified;
    /**
     * This object is a folder if size of <i>files</i> > 0
     */
    private ArrayList<KFile> files = new ArrayList<KFile>();

    public KFile(String name, Date modified) {
        this.name = name;
        this.modified = modified;
    }

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
     * @return True if found. False if not found
     * 
     * Path example : progin5/tes/Main.java
     */
    public boolean findFile(String path) {
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
                    return true;
                }
                
                return files.get(i).findFile(part[1]);
            }
        }

        return false;
    }

    public void AddFile(KFile file) {
        getFiles().add(file);
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
}