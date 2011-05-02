package kotakserver.model;

import java.net.Socket;

/**
 *
 * @author user
 */
public class KAddFile extends KMessage {

    public KAddFile(Socket socket) {
        super(socket);
    }

    @Override
    public boolean  run() {
        // TODO AddFile

        // Menerima pesan : addfile [repository] [last_revision] [path] [content]#

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

        return true;
    }

}
