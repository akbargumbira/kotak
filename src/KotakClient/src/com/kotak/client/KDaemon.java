package com.kotak.client;

import com.google.gson.Gson;
import com.kotak.util.MyThread;
import com.kotak.util.KLogger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.client.model.KAppData;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KGetFile;
import com.kotak.protocol.transfer.KTPClient;
import com.kotak.util.KFile;
import com.kotak.util.KFileSystem;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Rezan Achmad
 */
public class KDaemon extends MyThread {

    private String workingFolderPath = "";
    private String workingFolderName;
    private String repoPath;
    
    private String serverURL;
    private int serverPort;
    
    private String email;
    private String pass;
    
    private KTPClient ktp;

    public KDaemon(String email, String pass) {
        this.email = email;
        this.pass = pass;
        serverURL = KAppData.instance.getServerURL();
        serverPort = KAppData.instance.getServerPort();
    }

    @Override
    public void start() {
        try {
            // TODO Check Kotak directory
            workingFolderPath = KAppData.instance.getWorkingFolderPath();
            workingFolderName = KAppData.instance.getWorkingFolderName();
            repoPath = workingFolderPath + "/" + workingFolderName + "/" + email;
            File fileRepo = new File(repoPath);

            if (!fileRepo.exists()) {
                fileRepo = new File(repoPath);
                fileRepo.createNewFile();
                
                // Create client struct
                FileOutputStream fos = new FileOutputStream(repoPath + "/" + ".client");
                fos.write(new Gson().toJson(new KFile(email, new Date(fileRepo.lastModified()))).getBytes());
                fos.close();
                
                // Create server struct
                fos = new FileOutputStream(repoPath + "/" + ".server");
                fos.write(new Gson().toJson(new KFile(email, new Date(fileRepo.lastModified()))).getBytes());
                fos.close();
            }

            ktp = new KTPClient();
            // TODO Start KDaemon
            while (loop) {
                syncRepository();
            }
        } catch (IOException ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void syncRepository() {
        try {
            // TODO Synchronize server
            serverSync();

            // TODO Synchronize client
            clientSync();
        } catch (UnknownHostException ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean serverSync() throws UnknownHostException, IOException {
        // TODO Check
        
        // Create object check
        int revision = 0;
        KCheck message = new KCheck(email, pass, revision); 
        
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
            String json = part[3];
            
            // Struct in client
            KFile client = KFile.fromJSONFile(repoPath + "/.server");
            
            // Struct from server
            KFile server = KFile.fromJSONString(json);

            // TODO Delete 
            deleteInClient(email, client, server);

            // TODO Update
            updateInClient(email, client, server);
        }

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
            
            // Remove in structure
            struct.getParent().getFiles().remove(struct);
            //server.removeFile(tempPath);
        }
    }
    
    private boolean clientSync() throws FileNotFoundException, IOException {
        // TODO Check change in client
       
        // Compare saved structure and current structure

        deleteInServer(repoPath, KFile.fromJSONFile(repoPath + "/.client"));
        
        addToServer(repoPath, KFile.fromJSONFile(repoPath + "/.client"));
        
        return true;
    }
    
    /**
     * Delete file or folder in Client that not in Server
     * @param tempPath
     * @param fileClient Client structure.
     * @param fileServer Server structure. This is permanent to all recursive
     */
    private void deleteInClient(String tempPath, KFile fileClient, KFile fileServer) {
        ArrayList<KFile> files = fileClient.getFiles();
        int size = files.size();

        for (int i = 0; i < size; ++i) {
            String newPath = tempPath + "/" + files.get(i).getName();
            if (fileServer.isExist(newPath)) {
                deleteInClient(newPath, fileClient.getFiles().get(i), fileServer);
            } else {
                // TODO Delete File
                KFileSystem.delete(new File(newPath));
            }
        }
    }
    
    /**
     * Get file from Server that not in Client
     * @param tempPath
     * @param fileClient Client structure. This is permanent to all recursive
     * @param fileServer Server structure. 
     */
    private void updateInClient(String tempPath, KFile fileClient, KFile fileServer) {
        ArrayList<KFile> files = fileServer.getFiles();
        int size = files.size();
        KFile tempFile;
        
        for (int i = 0; i < size; ++i) {
            String newPath = tempPath + "/" + files.get(i).getName();
            tempFile = fileClient.findFile(newPath);
            
            if(tempFile == null || fileClient.getModified() != tempFile.getModified()) {
                try {
                    // TODO addfile
                    
                    // Set message
                    int revision = 0;
                    KGetFile message = new KGetFile(email, pass, newPath, revision);
                    
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
                        KFileSystem.save(newPath, part[1].getBytes());
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }
}
