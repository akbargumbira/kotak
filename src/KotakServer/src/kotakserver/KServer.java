/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class KServer extends KThread {

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public KServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void start() {
        try {
            // TODO Start

            // Get InputStream
            in = socket.getInputStream();

            // Get OutputStream
            out = socket.getOutputStream();

            while(socket.isConnected()) {
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(KServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
