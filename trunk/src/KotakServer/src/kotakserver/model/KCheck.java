/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver.model;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotakserver.database.QueryManagement;

/**
 *
 * @author user
 */
public class KCheck extends KMessage {

    public KCheck(String request) {
        super(request);
    }

    @Override
    public String  run() {
        // TODO run
        // Menerima pesan : check [email] [pass] [repository] [revision]
        String[] part = request.split(" ");
        
        String email = part[1];
        String pass = part[2];
        String repository = part[3];
        String revision = part[4];
        
        String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
        String queryLastRev = "SELECT revision_repo.structure, MAX(revision_repo.rev_num) FROM revision_repo LEFT JOIN repository ON revision_repo.repo_id=repository.id"
                + "WHERE repository.name ='"+email+"'";
        
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
             String LasRev = rsRev.getString("MAX(revision_repo.rev_num)");
             if (LasRev.equals(revision)) {
                 StringBuilder sb = new StringBuilder();
                 sb.append("success nochange");
                 response = sb.toString();
             }
             else {
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
            Logger.getLogger(KCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
