package com.kotak.message.process;
import com.google.gson.Gson;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KGetFile;
import com.kotak.message.model.KMessage;
import com.kotak.util.KFile;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.server.database.QueryManagement;
import com.kotak.util.FileSystemServer;

/**
 *
 * @author user
 */
public class KGetFileProcess extends KMessageProcess {

    public KGetFileProcess(KMessage request) {
        super(request);
    }

    @Override
    public String  run() {
        // TODO Run

        // Menerima pesan msg: getfile [email] [password] [repository] [path] [revision]
        
        String email = request.getEmail();
        String pass = request.getPass();
        String repository = request.getRepository();
        String path = ((KGetFile)request).getFilePath();
        int revision = ((KGetFile)request).getFileRevision();
        
        
        String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
        String queryStructure = "SELECT revision_repo.structure FROM revision_repo LEFT JOIN repository ON revision_repo.repo_id=repository.id"
                + "WHERE repository.name ='"+email+"' AND revision_repo.rev_num = '"+revision+"'";
        // [path] file yang direqeust, misal : progin5/kotak/Main.java

        // Check apakah [path] terdapat pada [repository] di revisi [revision]
            // Pengecekan melalui database

        // Jika ada, kirim pesan : success [filecontent]
            // File content adalah isi dari [path]
            // Cara penelusuran : lihat dokumentasi & tanya rezan

        // Jika tidak ada kirim pesan : failed [failed_msg]

        QueryManagement qM;        
        try {
            qM = new QueryManagement();
            ResultSet rs = qM.SELECT(queryPass);
            if (rs.next()) {
                ResultSet rsStructure = qM.SELECT(queryStructure);
                if (rs.next()) {
                    //File yang direquest ada
                    String struct =  rsStructure.getString("revision_repo.structure");
                    KFile file = (KFile)(new Gson()).fromJson(struct, KFile.class);
                    if (file.isExist(path)) { //file ada
                        FileSystemServer fsServer = new FileSystemServer();
                        byte[] fileBytes = fsServer.getFileContent(repository, revision, path);
                        if (fileBytes!=null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("success").append(fileBytes);
                            response = sb.toString();
                        } else { //ga ada file di server
                            StringBuilder sb = new StringBuilder();
                            sb.append("failed file_not_found");
                            response = sb.toString();
                        }
                            
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("failed no_such_requested_file");
                        response = sb.toString();
                    }
                        
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("failed no_such_structure");
                    response = sb.toString();
                }
            } else {
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