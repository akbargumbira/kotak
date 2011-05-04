/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.server;

import com.kotak.message.model.KAddFile;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KDelete;
import com.kotak.message.model.KGetFile;
import com.kotak.message.model.KMessage;
import com.kotak.message.model.KRegister;
import com.kotak.protocol.transfer.KTPServer;
import com.kotak.util.KThread;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.message.process.KAddFileProcess;
import com.kotak.message.process.KCheckProcess;
import com.kotak.message.process.KDeleteProcess;
import com.kotak.message.process.KGetFileProcess;

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
            KMessage request = ktp.getRequest();
            
            // Init
            String response = "failed";

            if (request.getClass() == KCheck.class) {
                response = (new KCheckProcess(request)).run();
            } else if (request.getClass() == KGetFile.class) {
                response = (new KGetFileProcess(request)).run();
            } else if (request.getClass() == KAddFile.class) {
                response = (new KAddFileProcess(request)).run();
            } else if (request.getClass() == KDelete.class) {
                response = (new KDeleteProcess(request)).run();
            } 

            // Send Response
            ktp.sendResponse(response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}