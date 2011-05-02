/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver.model;

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
        response = "failed";
        // TODO run
        
        // Menerima pesan : check [email] [pass] [repository] [revision]
        String[] part = request.split(" ");
        
        String email = part[1];
        String pass = part[2];
        String repository = part[3];
        String revision = part[4];
        
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

        return response;
    }
}
