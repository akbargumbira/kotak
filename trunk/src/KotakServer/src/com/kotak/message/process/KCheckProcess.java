/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kotak.message.process;

import com.kotak.message.model.KCheck;
import com.kotak.message.model.KMessage;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.server.database.QueryManagement;

/**
 *
 * @author user
 */
public class KCheckProcess extends KMessageProcess {

    public KCheckProcess(KMessage request) {
        super(request);
    }

    @Override
    public String  run() {
        // TODO run
        // Menerima pesan : check [email] [pass] [repository] [revision]
        
        String email = request.getEmail();
        String pass = request.getPass();
        int revision = ((KCheck)request).getClientLastRevision();
        
        String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
        String queryLastRev = "SELECT MAX(revision_repo.rev_num) FROM user LEFT JOIN revision_repo ON ser.id=revision_repo.user_id"
                + "WHERE user.email ='"+email+"'";
        
        //GAK PERLU
        /* // Periksa apakah email [email] memiliki repository [repository]
        // Jika tidak memiliki
            // kirim pesan msg : failed [email]_don't_have_repository_[repository]
            // close socket
            // return;
         */
        
        //Cek apakah email sesuai dengan passwordnya :
        QueryManagement qM;        
        try {
            qM = new QueryManagement();
            ResultSet rs = qM.SELECT(queryPass);
            if (rs.next()) {
              //Masuk ke proses check  
                // Jika [revision] sama dengan revisi terakhir [repository] di server
                    // Kirim pesan msg : nochange
                // Jika berbeda
                    // Kirim pesan msg : structure [revision] [struct_content]
                    // [struct_content] diperoleh dari tabel revision_repo
             ResultSet rsRev = qM.SELECT(queryLastRev);
             int LasRev = Integer.parseInt(rsRev.getString("MAX(revision_repo.rev_num)"));
             if (LasRev==revision) {
                 StringBuilder sb = new StringBuilder();
                 sb.append("success nochange");
                 response = sb.toString();
             }
             else {
                 String queryLastRevStructure = "SELECT revision_repo.structure FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id"
                + "WHERE user.email ='"+email+"' AND revision_repo.rev_num = '"+LasRev+"'";
                 ResultSet rsStructure = qM.SELECT(queryLastRevStructure);
                 StringBuilder sb = new StringBuilder();
                 String structure = rsRev.getString("revision_repo.structure");
                 sb.append("success structure ").append(LasRev).append(" ").append(structure);
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
