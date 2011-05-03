package com.kotak.client;

import com.kotak.util.MyThread;
import com.kotak.util.KLogger;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.client.model.KAppData;
import com.kotak.protocol.transfer.KTPClient;
import com.kotak.util.KFile;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class KDaemon extends MyThread {

    private String workingFolder = "";
    private String serverURL;
    private int serverPort;
    private String email;
    private String pass;
    private String repo;
    private KTPClient ktp;

    public KDaemon(String email, String pass) {
        this.email = email;
        this.pass = pass;
        serverURL = KAppData.instance.getServerURL();
        serverPort = KAppData.instance.getServerPort();
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
        String message = "check " + email + " " + pass + " " + email;
        
        // Send message and wait for response
        KLogger.writeln("send message : " + message);
        String response = ktp.sendRequest(serverURL, serverPort, message);
        KLogger.writeln("response : " + response);
        
        // Split the response
        String[] part = response.split(" ", 3);
        
        // Get prefix
        String prefix = (part != null && part.length > 0) ? part[0] : "";
        
        // Response is a structure?
        boolean isStructure = (part != null && part.length > 1 && part[1].equals("structure"));
        
        if(prefix.equals("success") && isStructure && part.length == 4 ) {
            // Get revision
            int revision = Integer.parseInt(part[2]);
            
            // Get structure form response
            String gson = part[3];

            // TODO Delete 
            delete(email, null, null);

            // TODO Update
            update(email, null, null);
        }

        return true;
    }

    /**
     * Delete file or folder in Client that not in Server
     * @param tempPath
     * @param fileClient Client structure.
     * @param fileServer Server structure. This is permanent to all recursive
     */
    private void delete(String tempPath, KFile fileClient, KFile fileServer) {
        ArrayList<KFile> files = fileClient.getFiles();
        int size = files.size();

        for (int i = 0; i < size; ++i) {
            String newPath = tempPath + "/" + files.get(i).getName();
            if (fileServer.isExist(newPath)) {
                delete(newPath, fileClient.getFiles().get(i), fileServer);
            } else {
                // TODO Delete File
            }
        }
    }
    
    /**
     * Get file from Server that not in Client
     * @param tempPath
     * @param fileClient Client structure. This is permanent to all recursive
     * @param fileServer Server structure. 
     */
    private void update(String tempPath, KFile fileClient, KFile fileServer) {
        ArrayList<KFile> files = fileServer.getFiles();
        int size = files.size();
        KFile tempFile;
        
        for (int i = 0; i < size; ++i) {
            String newPath = tempPath + "/" + files.get(i).getName();
            tempFile = fileClient.findFile(newPath);
            
            if(tempFile == null || fileClient.getModified() != tempFile.getModified()) {
                try {
                    // TODO addfile
                    // msg : getfile [email] [pass] [repository] [path] [revision]
                    
                    // Set message
                    int revision = 0;
                    String message = "getfile " + email + " " + pass + " " + email + " " + newPath + " " + revision;
                    
                    // Send message and wait for response
                    KLogger.writeln("send message : " + message);
                    String response = ktp.sendRequest(serverURL, serverPort, message);
                    KLogger.writeln("response : " + response);
                    
                    // Split the respons
                    String[] part = response.split(" ", 2);
                    
                    //Prefix
                    String prefix = (part != null && part.length > 0) ? part[0] : "";
                    
                    if (prefix.equals("success") && part.length == 2){
                        // TODO Save File to storage
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    
    /**
     * 
     * @param currentPath
     * @param old 
     */
    private void addToServer(String currentPath, KFile old) {
        File curr = new File(currentPath);
        if(curr.exists() && curr.lastModified() != old.getModified().getTime()) {
            if (curr.isFile()) {
                // TODO addfile
                // msg : addfile [email] [pass] [repository] [last_revision] [path] [content]
            } else {
                File[] files = curr.listFiles();
                int size = files.length;
                KFile temp;
                String name;
                
                for (int i = 0; i < size; ++i) {
                    name = files[i].getName();
                    temp = old.findFile(name);
                    if (temp != null) {
                        addToServer(name, temp);
                    } else {
                        // TODO addfile
                        // msg : addfile [email] [pass] [repository] [last_revision] [path] [content]
                    }
                }
            }
        }
    }

    /**
     * 
     * @param tempPath
     * @param struct 
     */
    private void deleteInServer(String tempPath, KFile struct) {
        File file = new File(tempPath);
        if (file.exists()) {
            ArrayList<KFile> kFiles = struct.getFiles();
            KFile kFile = null;
            int size = kFiles.size();
            String newPath = "";
            
            for (int i=0; i < size; ++i) {
                kFile = kFiles.get(i);
                newPath = tempPath + "/" + kFile.getName();
                deleteInServer(newPath,kFile);
            }
        } else {
            // TODO Send Delete
            // delete [email] [pass] [repository] [path] [last_revision]
            
            // TODO Recieva response
        }
    }
    
    private boolean isValid(String response) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void UpdateRepository() {
        
    }
}
