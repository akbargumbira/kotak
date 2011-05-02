/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver.model;

import java.net.Socket;

/**
 *
 * @author user
 */
public class KGetFile extends KMessage {

    public KGetFile(Socket socket) {
        super(socket);
    }

    @Override
    public boolean  run() {
        // TODO Run

        // Menerima pesan msg: getfile [email] [password] [repository] [path] [revision]
        // [path] file yang direqeust, misal : progin5/kotak/Main.java

        // Check apakah [path] terdapat pada [repository] di revisi [revision]
            // Pengecekan melalui database

        // Jika ada, kirim pesan : success [filecontent]
            // File content adalah isi dari [path]
            // Cara penelusuran : lihat dokumentasi & tanya rezan

        // Jika tidak ada kirim pesan : failed [failed_msg]

        return true;
    }

}
