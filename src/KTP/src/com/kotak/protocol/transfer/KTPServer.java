package com.kotak.protocol.transfer;

import com.kotak.message.model.KMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    public KMessage getRequest() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (KMessage) ois.readObject();
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