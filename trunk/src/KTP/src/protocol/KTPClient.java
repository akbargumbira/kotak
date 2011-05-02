package protocol;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author user
 */
public class KTPClient extends KTP {
    
    public void sendRequest(String address, int port) throws UnknownHostException, IOException {
        socket = new Socket(address, port);
        out = socket.getOutputStream();

        out.write((new Gson()).toJson(parameters).getBytes());
        out.close();
    }

    public String getResponse() throws IOException {
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