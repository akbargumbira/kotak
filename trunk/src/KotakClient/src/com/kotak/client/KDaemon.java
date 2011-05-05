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
import com.kotak.client.model.KRevision;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KDelete;
import com.kotak.message.model.KGetFile;
import com.kotak.protocol.transfer.KTPClient;
import com.kotak.util.KFile;
import com.kotak.util.KFileJSON;
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
            repoPath = KAppData.instance.getRepoPath();
            File fileRepo = new File(repoPath);

            if (!fileRepo.exists()) {
                fileRepo = new File(repoPath);
                if (!fileRepo.mkdir()) {
                    throw new Exception("Can't create dir");
                }

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
                try {
                    syncRepository();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void syncRepository() throws FileNotFoundException, ClassNotFoundException {
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

    private boolean serverSync() throws UnknownHostException, IOException, FileNotFoundException, ClassNotFoundException {
        // TODO Check
        
        // Create object check
        int revision = KRevision.getRevision();
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
            revision = Integer.parseInt(part[2]);
            
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
            
            // Save Revision
            KRevision.setRevision(revision);
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
        boolean sendMessage = false;
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
        
        if (sendMessage) {
            
        }
    }

    /**
     * Request for delete file / folder in Server
     * @param tempPath
     * @param clientStruct 
     */
    private void deleteInServer(String tempPath, KFile clientStruct) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File(tempPath);
        if (file.exists()) {
            ArrayList<KFileJSON> kFiles = clientStruct.getFiles();
            KFile kFile = null;
            int size = kFiles.size();
            String newPath = "";
            
            for (int i=0; i < size; ++i) {
                kFile = (KFile) kFiles.get(i);
                newPath = tempPath + "/" + kFile.getName();
                deleteInServer(newPath,kFile);
            }
        } else {
            // Build message
            KDelete message = new KDelete(email, pass, tempPath, KRevision.getRevision());
            
            // Send and wait for response
            KLogger.writeln(message.toString());
            String response = ktp.sendRequest(serverURL, serverPort, message); 
            KLogger.writeln(response);
            
            // TODO Parse response
            
            // TODO Remove in structure client
            clientStruct.getParent().getFiles().remove(clientStruct);
            
            // TODO Remove in structure server
            clientStruct.getParent().getFiles().remove(clientStruct);
        }
    }
    
    private boolean clientSync() throws FileNotFoundException, IOException, ClassNotFoundException {
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
        ArrayList<KFileJSON> files = fileClient.getFiles();
        int size = files.size();
        KFile kFile;

        for (int i = 0; i < size; ++i) {
            kFile = (KFile) files.get(i);
            String newPath = tempPath + "/" + kFile.getName();
            if (fileServer.isExist(newPath)) {
                deleteInClient(newPath, kFile, fileServer);
            } else {
                // Delete File in Storage
                KFileSystem.delete(new File(newPath));
                
                // Delete File in Structure
                kFile.getParent().removeFile(kFile.getName(), new Date(new File(tempPath).lastModified()));
            }
        }
    }
    
    /**
     * Get file from Server that not in Client
     * @param tempPath
     * @param fileClient Client structure. This is permanent to all recursive
     * @param fileServer Server structure. 
     */
    private void updateInClient(String tempPath, KFile fileClient, KFile fileServer) throws FileNotFoundException, ClassNotFoundException {
        ArrayList<KFileJSON> files = fileServer.getFiles();
        int size = files.size();
        KFile tempClient;
        KFile tempServer;
        
        for (int i = 0; i < size; ++i) {
            tempServer = (KFile) files.get(i);
            String newPath = tempPath + "/" + tempServer.getName();
            tempClient = fileClient.findFile(newPath);
            
            if(tempClient == null || tempServer.getModified() != tempClient.getModified()) {
                try {
                    // TODO addfile
                    
                    // Set message
                    int revision = KRevision.getRevision();
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
                        
                        // TODO Add file in structure
                        fileClient.removeFile(newPath, new Date(new File(tempPath).lastModified()));
                        fileClient.findFile(tempPath).addFile(tempServer);
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                updateInClient(newPath, fileClient, tempServer);
            }
        }
    }
}
