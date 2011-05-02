package kotakserver.model;

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
public abstract class KMessage {
    protected InputStream in;
    protected OutputStream out;
    protected Socket socket;

    public KMessage(Socket socket) {
        try {
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(KMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public abstract boolean  run();
}
