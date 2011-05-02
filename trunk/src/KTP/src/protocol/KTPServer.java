/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

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

        parameters = (new Gson()).fromJson(sb.toString(), HashMap.class);

        return sb.toString();
    }

    public void sendResponse(String response) throws IOException {
        out.write(response.getBytes());
        out.close();
        socket.close();
    }
}
