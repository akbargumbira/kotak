/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.client;

import com.kotak.util.MyThread;
import com.kotak.util.KLogger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.client.model.KAppData;
import com.kotak.client.model.KFile;
import com.kotak.transferprotocol.KTPClient;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author user
 */
public class KDaemon extends MyThread {

    private String workingFolder = "";
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private String URL;
    private int port;
    private String email;
    private String pass;
    private String repo;
    private KTPClient ktp;
    String response;
    String message;
    String[] part;
    String prefix;

    public KDaemon(String email, String pass) {
        this.email = email;
        this.pass = pass;
        URL = KAppData.instance.getServerURL();
        port = KAppData.instance.getServerPort();
        workingFolder = KAppData.instance.getWorkingFolder();
    }

    @Override
    public void start() {
        ktp = new KTPClient();
        // TODO Start KDaemon
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
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean serverSync() throws UnknownHostException, IOException {
        // TODO Check
        // Set msg : check [email] [pass] [repository] [client_revision]
        message = "check " + email + " " + pass + " " + email;
        
        // Send message and wait for response
        response = ktp.sendRequest(URL, port, message);
        
        // Split the response
        part = response.split(" ", 3);
        
        // Get prefix
        prefix = (part != null && part.length > 0) ? part[0] : "";
        
        // Response is a structure?
        boolean isStructure = (part != null && part.length > 1 && part[1].equals("structure"));
        
        if(prefix.equals("success") && isStructure && part.length == 4 ) {
            // Get revision
            int revision = Integer.parseInt(part[2]);
            
            // Get structure form response
            String gson = part[3];

            // TODO Compare with structure in client

            // TODO Delete file that not in new Structure

            // TODO Update file that modified in structure or Add file that only in structure
            // Foreach
                // TODO getfile
                // getfile [email] [pass] [repository] [path] [revision]

                // Wait for response
                // If something error (failed [failed_msg])
                    // return false
                // else msg : succes [filecontent]
                    // Add to storage

            // Update revision number and Structure
        }

        return true;
    }

    private void delete(String path, KFile kFile) {
        KFile server = new KFile(path, null);
        ArrayList<KFile> files = kFile.getFiles();
        int size = files.size();

        for (int i=0; i < size; ++i) {
            String newPath = path + "/" + files.get(i).getName();
            if(server.findFile(newPath)) {
                delete(path, kFile);
            } else {
                // Delete
            }
        }
    }
    
    private boolean clientSync() {
        // Check change in client
       
        // Compare saved structure and current structure

        // Foreach deleted file/folder
            // TODO delete
            // delete [email] [pass] [repository] [path] [last_revision]
            // if response is failed
                // return false
            // else if response is success
                // Update revision number
                // Update structure
        
        // Foreach add/updated file/folder
            // TODO addfile
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
