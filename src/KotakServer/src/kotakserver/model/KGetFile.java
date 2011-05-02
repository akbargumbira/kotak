/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver.model;

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
        response = "failed";
        // TODO Run

        // Menerima pesan msg: getfile [email] [password] [repository] [path] [revision]
        String[] part = request.split(" ");
        String email = part[1];
        String pass = part[2];
        String repository = part[3];
        String path = part[4];
        String revision = part[5];
        
        // [path] file yang direqeust, misal : progin5/kotak/Main.java

        // Check apakah [path] terdapat pada [repository] di revisi [revision]
            // Pengecekan melalui database

        // Jika ada, kirim pesan : success [filecontent]
            // File content adalah isi dari [path]
            // Cara penelusuran : lihat dokumentasi & tanya rezan

        // Jika tidak ada kirim pesan : failed [failed_msg]

        return response;
    }

}
