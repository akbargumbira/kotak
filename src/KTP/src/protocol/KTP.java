package protocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class KTP {
    public static int SUCCESS = 200;
    public static int CLIENT_ERROR = 400;
    public static int REQUEST_INVALID = 401;
    public static int SERVER_ERROR = 500;

    protected int status;
    protected Socket socket;
    protected InputStream in;
    protected OutputStream out;

    protected HashMap<String, String> parameters = new HashMap<String, String>();
//
//    public void addParameter(String key, String value) {
//        parameters.put(key, value);
//    }
//
//    public String getParameter(String key) {
//        return parameters.get(key);
//    }
}
