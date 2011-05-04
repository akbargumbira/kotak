package com.kotak.message.process;

import com.kotak.message.model.KAddFile;
import com.kotak.message.model.KMessage;
import com.kotak.server.ServerData;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.server.database.QueryManagement;
import com.kotak.util.KFile;
import com.kotak.util.KFileSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 *
 * @author user
 */
public class KAddFileProcess extends KMessageProcess {

    public KAddFileProcess(KMessage request) {
        super(request);
    }

    @Override
    public String  run() {
        response = "failed";
        // TODO AddFile

        // Menerima pesan : addfile [email] [pass] [repository] [last_revision] [path] [content]#
        
        String email = request.getEmail();
        String pass = request.getPass();
        String repository = request.getRepository();
        int last_revision = ((KAddFile)request).getClientLastRevision();
        String path = ((KAddFile)request).getFilePath();
       byte[] content = ((KAddFile)request).getFileContent();
        
        
        boolean isLocked = false;
        String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
        String queryLastRev = "SELECT repository.id, revision_repo.structure,MAX(revision_repo.rev_num) FROM revision_repo LEFT JOIN repository ON revision_repo.repo_id=repository.id"
                + "WHERE repository.name ='"+email+"'";
       
        
        // Periksa [email] [pass]
        // Jika tidak cocok
            // kirim pesan failed email_or_pass_is_wrong
            // return false

        // Periksa repository
        // Jika tidak ada
            // kirim pesan failed repository_not_exist
            // return false;

        // Periksa apakah user [email] memiliki [repository]
        // Jika tidak
            // kirim pesan failed repository_not_owned
            // return false;

        // Periksa apakah repository dilock
        // Jika ya
            // kirim pesan failed repository_is_locked
            // return false;

        // Periksa revision
        // Jika tidak sama
            // kirim pesan failed revision_not_same
            // return false

        // Lock repostory
        // Tambah File
            // File ditambahkan direpository [repository] pada folder r[revision]
            // Ubah struktur database dan update revisi
            // Jika gagal
                // Unlock repository
                // kirim pesan : failed add_failed
                // return false;
            // Jika sukses
                // kirim pesan : success [last_revision]
        // Unlock repository

        
        //Cek apakah email sesuai dengan passwordnya :
        QueryManagement qM;        
        try {
            qM = new QueryManagement();
            ResultSet rs = qM.SELECT(queryPass);
            if (rs.next()) {
             if (!isLocked) {
                 //Ga di lock
                 //Periksa revisi sama ga ma last revisi
                 ResultSet rsRev = qM.SELECT(queryLastRev);
                 int LasRev = Integer.parseInt(rsRev.getString("MAX(revision_repo.rev_num)"));
                 if (LasRev==last_revision) {
                     //Tambah File
                     isLocked = true;
                     
                     //Create folder r(last_revision+1)
                     File folder = new File(ServerData.baseURL+"/"+repository+"r"+(last_revision+1));
                     folder.mkdir();
                     
                     String strSavePath = ServerData.baseURL+"/"+repository+"r"+(last_revision+1)+"/"+path;
                     
                     KFileSystem.save(strSavePath, content);
                     
                     //Ubah struktur database
                     //Ambil struktur terakhir
                     String structure = rsRev.getString("revision_repo.structure");
                     KFile fileStructure = KFile.fromJSONString(structure);
                     KFile fileUpdated = fileStructure.findFile(path);
                     String structureUpdated;
                     if (fileUpdated != null) { //file update
                         fileUpdated.setModified(new Date(new File(strSavePath).lastModified()));
                         structureUpdated  = KFile.toJSON(fileStructure);
                         
                     } else { //new file added
                         //Parsing path buat dapet parent ma name
                         String[] part = path.split("/");
                         String fileName = part[part.length-1];
                         KFile fileAdded = new KFile(fileName, new Date(new File(strSavePath).lastModified()));
                         fileStructure.findFile(path.replace("/" + fileName, "")).addFile(fileAdded);
                         structureUpdated= KFile.toJSON(fileStructure);
                     }
                     //Update to database
                     String repo_id = rsRev.getString("repository.id");
                     String queryInsert = "INSERT INTO revision_repo ('repo_id','rev_num','structure')"
                             + "VALUES (' '"+repo_id+"' ',' '"+(last_revision+1)+"' ',' '"+structureUpdated+"' ')";
                     if (qM.INSERT(queryInsert)==0) { //insert berhasil
                         StringBuilder sb = new StringBuilder();
                         sb.append("success ").append(last_revision+1);
                         response = sb.toString();
                     } else {
                         StringBuilder sb = new StringBuilder();
                         sb.append("failed add_failed");
                         response = sb.toString();
                     }
                     isLocked = false;
                 } else {
                     StringBuilder sb = new StringBuilder();
                     sb.append("failed revision_not_same");
                     response = sb.toString();
                 }
 
             } else {
                 StringBuilder sb = new StringBuilder();
                 sb.append("failed repository_is_locked");
                 response = sb.toString(); 
             }
            }
            else {
                StringBuilder sb = new StringBuilder();
                sb.append("failed email_or_pass_is_wrong");
                response = sb.toString(); 
            }
                
        } catch (Exception ex) {
            Logger.getLogger(KCheckProcess.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return response;
    }
}
