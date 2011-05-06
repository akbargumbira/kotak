/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.server;

import java.io.*;

/**
 *
 * @author user
 */
public class FileSystemServer {
    public FileSystemServer() {
    }
    
    public byte[] getFileContent(String repository, int rev_num, String path) throws FileNotFoundException, IOException {
        String filePath = ServerData.baseURL+"/"+repository;
        
        for (int i = rev_num; i > 0; --i) {
            File res = new File(filePath + "/r" + i + "/" + path);
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
