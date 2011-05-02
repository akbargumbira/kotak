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
public class KCheck extends KMessage {

    public KCheck(Socket socket) {
        super(socket);
    }

    @Override
    public boolean  run() {
        // TODO run
        // Menerima pesan : check [email] [pass] [repository] [revision]

        // Periksa apakah email [email] memiliki repository [repository]
        // Jika tidak memiliki
            // kirim pesan msg : failed [email]_don't_have_repository_[repository]
            // close socket
            // return;

        // Jika [revision] sama dengan revisi terakhir [repository] di server
            // Kirim pesan msg : nochange [repository]
        // Jika berbeda
            // Kirim pesan msg : structure [repository] [revision] [struct_content]
            // [struct_content] diperoleh dari tabel revision_repo

        return true;
    }
}
