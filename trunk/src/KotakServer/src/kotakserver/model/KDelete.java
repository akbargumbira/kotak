package kotakserver.model;

/**
 *
 * @author user
 */
public class KDelete extends KMessage {

    public KDelete(String request) {
        super(request);
    }

    @Override
    public String  run() {
        response = "failed";
        // TODO Run

        // Menerima pesan delete [email] [pass] [repository] [path] [last_revision]
        String[] part = request.split(" ");
      
        String email = part[1];
        String pass = part[2];
        String repository = part[3];
        String path = part[4];
        String last_revision = part[5];
        
        String queryPass = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
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

        return response;
    }

}
