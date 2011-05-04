package com.kotak.client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author user
 */
public class KRevision {
    private static String fileName = ".revision";
    private static String filePath = "";
    
    private static void init() throws FileNotFoundException, IOException, ClassNotFoundException {
        KAppData.load();
        filePath = KAppData.instance.getRepoPath() + "/" + fileName;
    }
    
    public static void setRevision(int number) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (number > 0) {
            init();
            
            File file = new File(filePath);
            if (file.exists()) {
                FileOutputStream fos = new FileOutputStream(file);
                DataOutputStream dos = new DataOutputStream(fos);
                dos.writeInt(number);
                dos.close();
            }
            
        }
    }
    
    public static int getRevision() throws FileNotFoundException, IOException, ClassNotFoundException {
        init();
        
        File file = new File(filePath);
        
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            return dis.readInt();
        }
        
        return 0;
    }
}
