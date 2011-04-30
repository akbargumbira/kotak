/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Daemon extends MyThread {

    private String workingFolder = "";
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private String URL = "127.0.0.1";
    private int port = 10000;

    public Daemon() {
    }

    @Override
    public void start() {
        // TODO Start Daemon
        
        try {
            // TODO Create new socket

            // TODO Get Server URL and Port
            URL = "127.0.0.1";
            port = 10000;

            // Waiting for connection
            socket = new Socket(URL, port);

            // Connection is established
            System.out.println("Connection is established");

            // Get InputStream
            in = socket.getInputStream();

            // Get OutputStream
            out = socket.getOutputStream();

            //
        } catch (UnknownHostException ex) {
            Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
