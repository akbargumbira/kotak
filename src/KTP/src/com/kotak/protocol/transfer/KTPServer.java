package com.kotak.protocol.transfer;

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

    /**
     * Get request from a client
     * @return Request from client
     * @throws IOException 
     */
    public String getRequest() throws IOException {
        int temp;
        StringBuilder sb = new StringBuilder();

        while((temp = in.read()) > -1) {
            sb.append((char)temp);
        }

        return sb.toString();
    }

    /**
     * Send response to client
     * @param response Response to client
     * @throws IOException 
     */
    public void sendResponse(String response) throws IOException {
        sendResponse(response.getBytes());
    }
    
    /**
     * Send response to client
     * @param bytes Response (in bytes) for client
     * @throws IOException 
     */
    public void sendResponse(byte[] bytes) throws IOException {
        out.write(bytes);
        out.close();
        socket.close();
    }
}