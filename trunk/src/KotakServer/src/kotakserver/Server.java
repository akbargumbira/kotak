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
public class Server extends MyThread {

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public Server(Socket socket) {
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

            // Just Test :p
            int temp;
            while((temp = in.read()) > -1) {
                System.out.print((char)temp);
            }

            out.write("Hai".getBytes());
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
