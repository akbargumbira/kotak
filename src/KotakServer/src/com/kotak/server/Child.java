/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.server;

import com.kotak.protocol.transfer.KTPServer;
import com.kotak.util.KThread;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.server.message.KAddFile;
import com.kotak.server.message.KCheck;
import com.kotak.server.message.KDelete;
import com.kotak.server.message.KGetFile;

/**
 *
 * @author user
 */
public class Child extends KThread {

    private KTPServer ktp;

    public Child(KTPServer ktp) {
        this.ktp = ktp;
    }

    @Override
    public void start() {
        try {
            // Get Request
            String request = ktp.getRequest();
            
            // Init
            String response = "failed";
            
            // Split until find FIRST space
            String[] sArr = request.split(" ", 1);
            
            // Get message
            String msg = (sArr.length > 0) ? sArr[0] : "";

            if (msg.equals("check")) {
                response = (new KCheck(request)).run();
            } else if (msg.equals("getfile")) {
                response = (new KGetFile(request)).run();
            } else if (msg.equals("addfile")) {
                response = (new KAddFile(request)).run();
            } else if (msg.equals("delete")) {
                response = (new KDelete(request)).run();
            }

            // Send Response
            ktp.sendResponse(response);
        } catch (IOException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}