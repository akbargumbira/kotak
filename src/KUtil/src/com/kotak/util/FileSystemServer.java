/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.util;

import com.sun.org.apache.bcel.internal.util.ByteSequence;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class FileSystemServer {
    public static String baseURL = "";

    public FileSystemServer() {
    }
    
    public static byte[] getFileContent(String repository, int rev_num, String path) throws FileNotFoundException, IOException {
        String filePath = baseURL+repository;
        
        for (int i = rev_num; i > 0; --i) {
            File res = new File(filePath + "/r" + rev_num + "/" + path);
            if (res.exists()) {
                FileInputStream in = new FileInputStream(res);
                byte[] bytes = new byte[(int)res.length()];
                in.read(bytes);
                in.close();
                return bytes;
            }
        }   
        return null;
    }
}
