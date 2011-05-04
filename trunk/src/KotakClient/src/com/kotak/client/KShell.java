/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.client;

import com.kotak.client.model.KAppData;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KMessage;
import com.kotak.protocol.transfer.KTPClient;
import com.kotak.util.KFileSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.management.FileSystem;

/**
 *
 * @author user
 */
public class KShell {
    Scanner in;
    String command;
    String response;
    KTPClient ktp;
    String URL;
    int port;
    
    public void run() {
        in = new Scanner(System.in);
        command = "";
        
        try {
            KAppData.instance = (KAppData)KAppData.load();
            URL = KAppData.instance.getServerURL();
            port = KAppData.instance.getServerPort();
            ktp = new KTPClient();
            KMessage message = null;
            
            while(true) {
                command = in.nextLine();
                String[] part = command.split("  *");
                
                if (part.length < 1) {
                    continue;
                }
                
                if (part[0].equals("check") && part.length == 5) {
                    message = new KCheck(part[1], part[2], part[3], Integer.parseInt(part[4]));
                }
                else if(part[0].equals("addfile") && part.length == 6) {
                    byte[] bytes = KFileSystem.open(part[5]);
                }
                
                response = ktp.sendRequest(URL, port, message);
                System.out.println("response : " + response);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KShell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KShell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KShell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
