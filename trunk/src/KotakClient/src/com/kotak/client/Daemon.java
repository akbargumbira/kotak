/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.client.model.AppData;

/**
 *
 * @author user
 */
public class Daemon extends MyThread {

    private String workingFolder = "";
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private String URL;
    private int port;
    private String email;
    private String pass;
    private String repo;

    public Daemon(String email, String pass) {
        this.email = email;
        this.pass = pass;
        URL = AppData.instance.getServerURL();
        port = AppData.instance.getServerPort();
        workingFolder = AppData.instance.getWorkingFolder();
    }

    @Override
    public void start() {
        // TODO Start Daemon
        while (loop) {
            syncRepository();
        }
    }

    private void syncRepository() {
        try {
            KLogger.writeln("Waiting for connection from " + URL + ":" + port);
            // Waiting for connection
            socket = new Socket(URL, port);

            KLogger.writeln("Connection is established");
            // Connection is established

            // Get InputStream
            in = socket.getInputStream();

            // Get OutputStream
            out = socket.getOutputStream();

            // Synchronize server
            serverSync();

            // Synchronize client
            clientSync();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean serverSync() {
        // Send Check
        // msg : check [email] [pass] [repository] [revision]
        
        // if succes & response not null
            // Get structure form response

            // Compare with structure in client

            // Delete file that not in new Structure

            // Update file that modified in structure or Add file that only in structure
            // Foreach
                // getfile [email] [pass] [repository] [path] [revision]

                // Wait for response
                // If something error (failed [failed_msg])
                    // return false
                // else msg : succes [filecontent]
                    // Add to storage

            // Update revision number and Structure
        // else
            // return false;

        return true;
    }

    private boolean clientSync() {
        // Check change in client
       
        // Compare saved structure and current structure

        // Foreach deleted file/folder
            // delete [email] [pass] [repository] [path] [last_revision]
            // if response is failed
                // return false
            // else if response is success
                // Update revision number
                // Update structure
        
        // Foreach add/updated file/folder
            // msg : addfile [email] [pass] [repository] [last_revision] [path] [content]
            // If reponse is failed
                // return false
            // else if response is success
                // Update revision number
                // Update structure
        
        return true;
    }

    private boolean isValid(String response) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void UpdateRepository() {
        
    }
}
