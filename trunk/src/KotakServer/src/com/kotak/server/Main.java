package com.kotak.server;

import com.google.gson.Gson;
import com.kotak.protocol.transfer.KTPServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.util.KFile;
import com.kotak.util.KFileJSON;

/**
 *
 * @author user
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Json Example
        KFile root = new KFile("rezanachmad@gmail.com", (new Date()));

        System.out.println("Gson : " + (new Gson().toJson(root)));

        int port = 10000;

        try {
            ServerSocket ss = new ServerSocket(port);

            // Loop forever until die
            while (true) {
                // Waiting for connection
                Socket socket = ss.accept();

                // There is a connection, serve it!
                (new Child(new KTPServer(socket))).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
