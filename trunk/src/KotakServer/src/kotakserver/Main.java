/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port = 7000;

        try {
            ServerSocket ss = new ServerSocket(port);

            // Loop forever until die
            while (true) {
                // Waiting for connection
                Socket socket = ss.accept();

                // There is a connection, serve it!
                (new Server(socket)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
