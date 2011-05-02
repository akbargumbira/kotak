/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kotakserver;

import com.kotak.transferprotocol.KTPServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotakserver.model.KAddFile;
import kotakserver.model.KCheck;
import kotakserver.model.KDelete;
import kotakserver.model.KGetFile;

/**
 *
 * @author user
 */
public class Child extends KThread {

    private KTPServer ktp;

    public Child(KTPServer ktp) {
        this.ktp = ktp;
    }

    @Override
    public void start() {
        try {
            String request = ktp.getRequest();
            String response = "failed";
            String[] sArr = request.split(" ", 1);
            String msg = (sArr.length > 0) ? sArr[0] : "";

            if (msg.equals("check")) {
                response = (new KCheck(request)).run();
            } else if (msg.equals("getfile")) {
                response = (new KGetFile(request)).run();
            } else if (msg.equals("addfile")) {
                response = (new KAddFile(request)).run();
            } else if (msg.equals("delete")) {
                response = (new KDelete(request)).run();
            }

            ktp.sendResponse(response);
        } catch (IOException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}