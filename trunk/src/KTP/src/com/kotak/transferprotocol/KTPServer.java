package com.kotak.transferprotocol;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author user
 */
public class KTPServer extends KTP {

    public KTPServer(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public String getRequest() throws IOException {
        int temp;
        StringBuilder sb = new StringBuilder();

        while((temp = in.read()) > -1) {
            sb.append((char)temp);
        }

        return sb.toString();
    }

    public void sendResponse(String response) throws IOException {
        out.write(response.getBytes());
        out.close();
        socket.close();
    }
}
