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
        String response = null;
        // Menerima pesan : check [email] [pass] [repository] [revision]
        String[] part = request.split(" ");
        
        String email = part[1];
        String pass = part[2];
        String repository = part[3];
        String revision = part[4];
        
        String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
      
        
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
                    // Kirim pesan msg : nochange [repository]
                // Jika berbeda
                    // Kirim pesan msg : structure [repository] [revision] [struct_content]
                    // [struct_content] diperoleh dari tabel revision_repo
             

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
