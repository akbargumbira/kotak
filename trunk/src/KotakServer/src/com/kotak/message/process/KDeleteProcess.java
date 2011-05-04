package com.kotak.message.process;

import com.kotak.message.model.KDelete;
import com.kotak.message.model.KMessage;
import com.kotak.server.database.QueryManagement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class KDeleteProcess extends KMessageProcess {

    public KDeleteProcess(KMessage request) {
        super(request);
    }

    @Override
    public String run() {
            response = "failed";
            // TODO Run

            // Menerima pesan delete [email] [pass] [repository] [path] [client_last_revision]
            String email = request.getEmail();
            String pass = request.getPass();
            String repository = request.getRepository();
            String path = ((KDelete)request).getFilePath();
            int last_revision = ((KDelete)request).getClientLastRevision();
        
        
            
            boolean isLocked = false;
            String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
            String queryLastRev = "SELECT MAX(revision_repo.rev_num) FROM revision_repo LEFT JOIN repository ON revision_repo.repo_id=repository.id"
                    + "WHERE repository.name ='"+email+"'";

            // Periksa email dan pass
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

            // Periksa revision [last_revision]
            // Jika tidak sama
                // kirim pesan : failed revision_not_same
                // return false;

            // Lock repostory
            // Lakukan penghapusan
            // Penghapusan hanya dilakukan di struktur database
            // jika gagal
                // kirim pesan : failed delete_failed
                // Unlock repository
                // return false;
            // Jika sukses
                // kirim pesan : success [last_revision]
            // Unlock repository
            
            try {
            QueryManagement qM;

            qM = new QueryManagement();
            ResultSet rs = qM.SELECT(queryPass);
            if (rs.next()) {
                if (!isLocked) {
                    //Ga di lock
                    //Periksa revisi sama ga ma last revisi
                    ResultSet rsRev = qM.SELECT(queryLastRev);
                    int LasRev = Integer.parseInt(rsRev.getString("MAX(revision_repo.rev_num)"));
                    if (LasRev==last_revision) {
                        //Hapus File
                        isLocked = true;



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
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("failed email_or_pass_is_wrong");
                response = sb.toString();
            }

        } catch (Exception ex) {
            Logger.getLogger(KDeleteProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return response;
    }
}
