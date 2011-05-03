/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver.model;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotakserver.database.QueryManagement;

/**
 *
 * @author user
 */
public class KGetFile extends KMessage {

    public KGetFile(String request) {
        super(request);
    }

    @Override
    public String  run() {
        // TODO Run

        // Menerima pesan msg: getfile [email] [password] [repository] [path] [revision]
        String[] part = request.split(" ");
        String email = part[1];
        String pass = part[2];
        String repository = part[3];
        String path = part[4];
        String revision = part[5];
        
        String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
        String queryStructure = "SELECT revision_repo.structure FROM revision_repo LEFT JOIN repository ON revision_repo.repo_id=repository.id"
                + "WHERE repository.name ='"+email+"' AND revision_repo.rev_num = '"+revision+"' ";
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
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("failed requested_file_not_exist");
                    response = sb.toString();
                }
            } else {
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
