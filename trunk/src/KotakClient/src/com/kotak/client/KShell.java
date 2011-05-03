/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.client;

import com.kotak.client.model.KAppData;
import com.kotak.protocol.transfer.KTPClient;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            
            while(true) {
                command = in.nextLine();
                response = ktp.sendRequest(command, port, command);
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
