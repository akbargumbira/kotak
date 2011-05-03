package com.kotak.transferprotocol;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author user
 */
public class KTPClient extends KTP {
    
    /**
     * Send request
     * @param address Server address
     * @param port Server port
     * @param message String to send
     * @return Response from server
     * @throws UnknownHostException
     * @throws IOException 
     */
    public String sendRequest(String address, int port, String message) throws UnknownHostException, IOException {
        socket = new Socket(address, port);
        out = socket.getOutputStream();

        out.write(message.getBytes());
        out.close();
        
        return getResponse();
    }

    private String getResponse() throws IOException {
        StringBuilder sb = new StringBuilder();
        int temp;
        in = socket.getInputStream();

        while ((temp = in.read()) > 0) {
            sb.append((char)temp);
        }

        socket.close();
        
        return sb.toString();
    }
}