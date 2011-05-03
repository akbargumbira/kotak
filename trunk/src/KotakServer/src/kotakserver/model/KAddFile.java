package kotakserver.model;

import java.sql.ResultSet;
import kotakserver.database.QueryManagement;

/**
 *
 * @author user
 */
public class KAddFile extends KMessage {

    public KAddFile(String request) {
        super(request);
    }

    @Override
    public String  run() {
        response = "failed";
        // TODO AddFile

        // Menerima pesan : addfile [repository] [last_revision] [path] [content]#
        String[] part = request.split(" ");

        String repository = part[0];
        String last_revision = part[1];
        String path = part[2];
        String content = part[3];
        
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
