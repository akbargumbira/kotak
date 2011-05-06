package com.kotak.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class KLogger {

    public static String fileName = "log.txt";
    public static int maxChar = 1000;
    //public static

    public static void writeln(String string){
        try {
            if (string.length() > maxChar) {
                string = string.substring(0, maxChar);
            }
            
            // Write to file
            FileOutputStream fos = new FileOutputStream(fileName, true);
            fos.write((new Date()).toString().getBytes());
            fos.write(string.getBytes());
            fos.write("\n".getBytes());
            fos.close();

            // Write to system
            System.out.println(string);
            
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(KLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(KLogger.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void writeln(Class _class, String message) {
        writeln(_class + " : " + message);
    }
}
