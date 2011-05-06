package com.kotak.client;

import com.kotak.util.MyThread;
import com.kotak.util.KLogger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.client.model.KAppData;
import com.kotak.client.model.KRevision;
import com.kotak.message.model.KAddFile;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KDelete;
import com.kotak.message.model.KGetFile;
import com.kotak.protocol.transfer.KTPClient;
import com.kotak.util.KFile;
import com.kotak.util.KFileJSON;
import com.kotak.util.KFileSystem;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

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
    private KFile structClient;
    private KFile structServer;

    public KDaemon(String email, String pass) {
        this.email = email;
        this.pass = pass;
        serverURL = KAppData.getInstance().getServerURL();
        serverPort = KAppData.getInstance().getServerPort();
    }

    @Override
    public void start() {
        try {
            // TODO Check Kotak directory
            workingFolderPath = KAppData.getInstance().getWorkingFolderPath();
            workingFolderName = KAppData.getInstance().getWorkingFolderName();
            repoPath = KAppData.getInstance().getRepoPath();
            File fileRepo = new File(repoPath);

            if (!fileRepo.exists()) {
                fileRepo = new File(repoPath);
                if (!fileRepo.mkdirs()) {
                    throw new Exception("Can't create dir");
                }

                // Create client struct
                FileOutputStream fos = new FileOutputStream(repoPath + "/" + ".client", false);
                fos.write(new KFile(email, new Date(fileRepo.lastModified()), false).toJSON().getBytes());
                fos.close();

                // Create server struct
                fos = new FileOutputStream(repoPath + "/" + ".server", false);
                fos.write(new KFile(email, new Date(fileRepo.lastModified()), false).toJSON().getBytes());
                fos.close();
                
                // Revision
                KRevision.setRevision(0);
            }

            // Init
            ktp = new KTPClient();
            
            // Struct server in client
            structServer = KFile.fromJSONFile(repoPath + "/.server");
            
            // Struct client
            structClient = KFile.fromJSONFile(repoPath + "/.client");
            
            // Start Sync
            syncRepositoryDebug();
            
        } catch (IOException ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void saveStructClient() throws FileNotFoundException, IOException {
        // Create client struct
        FileOutputStream fos = new FileOutputStream(repoPath + "/" + ".client", false);
        fos.write(structClient.toJSON().getBytes());
        fos.close();
    }
    
    private void saveStructServer() throws FileNotFoundException, IOException {
        // Create server struct
        FileOutputStream fos = new FileOutputStream(repoPath + "/" + ".server", false);
        fos.write(structServer.toJSON().getBytes());
        fos.close();
    }
    
    private void syncRepository() throws FileNotFoundException, ClassNotFoundException {
        while (true) {
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
    }
    
    private void syncRepositoryDebug() throws FileNotFoundException, ClassNotFoundException, Exception {
        Scanner in;
        String command = "";
        boolean finish = false;
        while (!finish) {
            try {
                // Get Command
                System.out.println("server | client");
                in = new Scanner(System.in);
                command = in.nextLine();

                if ("server".equals(command)) {
                    // TODO Synchronize server
                    serverSync();
                } else if ("client".equals(command)) {
                    // TODO Synchronize client
                    clientSync();
                } else if ("break".equals(command)) {
                    finish = true;
                } else {
                    throw new Exception("Command not found.");
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        KLogger.writeln("Split response");
        String[] part = response.split(" ", 4);
        
        // Get prefix
        String prefix = (part != null && part.length > 0) ? part[0] : "";
        KLogger.writeln("prefix : " + prefix);
        
        // Response is a structure?
        boolean isStructure = (part != null && part.length > 1 && part[1].equals("structure"));
        KLogger.writeln("is structure : " + isStructure);
        
        if(prefix.equals("success") && isStructure && part.length == 4 ) {
            // Get revision
            revision = Integer.parseInt(part[2]);
            
            // Get structure form response
            String json = part[3];
            
            // Struct from server
            KFile server = KFile.fromJSONString(json);

            // TODO Delete 
            KLogger.writeln("delete in client");
            deleteInClient("", structServer, server);

            // TODO Update
            updateInClient("", server, revision);
            
            // Save All
            KRevision.setRevision(revision);
            KLogger.writeln("revision : " + revision);
        }
        
        KLogger.writeln("struct client : " + structClient.getTree());
        KLogger.writeln("struct server : " + structServer.getTree());
        saveStructClient();
        saveStructServer();

        return true;
    }
    
    private void addFile(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        // Get content of file
        byte[] bytes = KFileSystem.open(repoPath + "/" + path);
        
        // Make message
        KAddFile addFile = new KAddFile(email, pass, KRevision.getRevision(), path, bytes);
        
        // Send Message and wait for respon
        String response = ktp.sendRequest(serverURL, serverPort, addFile);
        
        
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
     * @param structClient 
     */
    private void deleteInServer(String tempPath, KFile structClient) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File(repoPath + "/" + tempPath);
        if ("".equals(tempPath)) {
            file = new File(repoPath);
        }
        
        if (file.exists()) {
            LinkedList<KFileJSON> kFiles = structClient.getFiles();
            KFile kFile = null;
            String newPath = "";
            
            for (int i=0; i < kFiles.size(); ++i) {
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
            // Get time and revision
            
            // TODO Remove in structure client
            structClient.getParent().getFiles().remove(structClient);
            
            // TODO Remove in structure server
            structClient.getParent().getFiles().remove(structClient);
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
     * @param fileClient Server structure in client.
     * @param fileServer Server structure. This is permanent to all recursive
     */
    private void deleteInClient(String tempPath, KFile fileClient, KFile fileServer) {
        LinkedList<KFileJSON> files = fileClient.getFiles();
        KFile tempClient;
        KFile tempServer;
        KFile tempServerParent;

        for (int i = 0; i < files.size(); ++i) {
            tempClient = (KFile) files.get(i);
            
            String newPath;
            if ("".equals(tempPath)) {
                newPath = tempClient.getName();
            } else {
                newPath = tempPath + "/" + tempClient.getName();
            }
            
            tempServer = fileServer.findFile(newPath);
            tempServerParent = fileServer.findFile(tempPath);
            if("".equals(tempPath)) {
                tempServerParent = fileServer;
            }
            if (tempServer != null) {
                deleteInClient(newPath, tempClient, fileServer);
            } else {
                // Delete File in Storage
                KFileSystem.delete(new File(KAppData.getInstance().getRepoPath() + "/" + newPath));
                File file = new File(KAppData.getInstance().getRepoPath() + "/" + tempPath);
                
                // Delete File in Structure
                structServer.removeFile(newPath, tempServerParent.getModified());
                structClient.removeFile(newPath, new Date(file.lastModified()));
            }
        }
    }
    
    /**
     * Get file from Server that not in Client
     * @param tempPath
     * @param structServer Client structure. This is permanent to all recursive
     * @param fileServer Server structure. 
     */
    private void updateInClient(String tempPath, KFile fileServer, int revision) throws FileNotFoundException, ClassNotFoundException {
        LinkedList<KFileJSON> files = fileServer.getFiles();
        int size = files.size();
        KFile tempClient;
        KFile tempServer;
        
        for (int i = 0; i < size; ++i) {
            tempServer = (KFile) files.get(i);
            
            String newPath = tempPath +"/" + tempServer.getName();
            if ("".equals(tempPath)) {
                newPath = tempServer.getName();
            }
            
            KLogger.writeln("temp path : "  + tempPath);
            KLogger.writeln("new path : "  + newPath);
            
            tempClient = structServer.findFile(newPath);
            
            if(tempClient == null || tempServer.getModified() != tempClient.getModified()) {
                try {
                    if (tempServer.isDir()) {
                        KLogger.writeln(tempServer.getName() + " is a directory");
                        
                        // Create directory
                        File dir = new File(KAppData.getInstance().getRepoPath() + "/" + tempPath, tempServer.getName());
                        dir.mkdir();
                        KLogger.writeln("Create directory " + tempServer.getName() + " is success");
                        
                        // Add to file logically
                        structServer.mkdirs(newPath, tempServer.getModified());
                        structClient.mkdirs(newPath, new Date(dir.lastModified()));
                        KLogger.writeln("struct server : " + structServer.getTree());
                        KLogger.writeln("struct client : " + structClient.getTree());
                        
                        // Update again
                        updateInClient(newPath, tempServer, revision);
                    } else {
                        KLogger.writeln(tempServer.getName() + " is a file");
                        // TODO addfile

                        // Set message
                        KGetFile message = new KGetFile(email, pass, newPath, revision);

                        // Send message and wait for response
                        KLogger.writeln("send message : " + message);
                        String response = ktp.sendRequest(serverURL, serverPort, message);
                        KLogger.writeln("response : " + response);

                        // Split the respons
                        String[] part = response.split(" ", 2);

                        //Prefix
                        String prefix = (part != null && part.length > 0) ? part[0] : "";
                        KLogger.writeln("prefix : " + prefix);

                        if (prefix.equals("success") && part.length == 2){
                            // TODO Save File to storage
                            KFileSystem.save(KAppData.getInstance().getRepoPath() + "/" +  newPath, part[1].getBytes());
                            
                            // File
                            File file = new File(KAppData.getInstance().getRepoPath() + "/" +  newPath) ;

                            // TODO Add file in structure
                            structServer.removeFile(newPath, new Date(new File(tempPath).lastModified()));
                            if (!"".equals(tempPath)) {
                                structServer.findFile(tempPath).addFile(tempServer);
                                structClient.findFile(tempPath).addFile(new KFile(tempServer.getName(), new Date(file.lastModified()), true));
                            } else {
                                structServer.addFile(tempServer);
                                structClient.addFile(new KFile(tempServer.getName(), new Date(file.lastModified()), true));
                            }
                        }
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(KDaemon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                updateInClient(newPath, tempServer, revision);
            }
        }
    }
}
