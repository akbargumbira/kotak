/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.client;

import com.kotak.client.model.KAppData;
import com.kotak.message.model.KAddFile;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KDelete;
import com.kotak.message.model.KGetFile;
import com.kotak.message.model.KMessage;
import com.kotak.protocol.transfer.KTPClient;
import com.kotak.util.KFile;
import com.kotak.util.KFileSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class KShell {

    Scanner in;
    String command;
    String response;
    KTPClient ktp;
    String URL;
    int port;

    public void run() {
        command = "";
        in = new Scanner(System.in);

        boolean finish = false;
        while (!finish) {
            System.out.println("message | sync");
            command = in.nextLine();
            if ("message".equals(command)) {
                shellMessage();
            } else if ("sync".equals(command)) {
                shellSync();
            } else if ("break".equals(command)) {
                finish = true;
            }
        }
    }

    public void shellSync() {
        new KDaemon(KAppData.getInstance().getEmail(), KAppData.getInstance().getPassword()).start();
    }

    public void shellMessage() {
        System.out.println("Shell Message");
        boolean finish = false;
        while (!finish) {
            command = "";

            try {
                URL = KAppData.getInstance().getServerURL();
                port = KAppData.getInstance().getServerPort();
                ktp = new KTPClient();
                KMessage message = null;
                command = in.nextLine();
                String[] part = command.split("  *");

                if (part.length < 1) {
                    continue;
                }

                String action = part[0];

                if (action.equals("check") && part.length == 4) {
                    message = new KCheck(part[1], part[2], Integer.parseInt(part[3]));
                } else if (action.equals("getfile") && part.length == 5) {
                    message = new KGetFile(part[1], part[2], part[3], Integer.parseInt(part[4]));
                } else if (action.equals("addfile") && part.length == 5) {
                    byte[] bytes = KFileSystem.open(KAppData.getInstance().getRepoPath() + "/" + part[4]);
                    message = new KAddFile(part[1], part[2], Integer.parseInt(part[3]), part[4], bytes);
                } else if (action.equals("delete") && part.length == 5) {
                    message = new KDelete(part[1], part[2], part[3], Integer.parseInt(part[4]));
                } else if (action.equals("break")) {
                    finish = true;
                    break;
                } else {
                    throw new Exception("Command Not found");
                }

                response = ktp.sendRequest(URL, port, message);
                System.out.println("response : " + response);

                if ("check".equals(action)) {
                    String partRes[] = response.split(" ", 4);
                    if (partRes.length == 4) {
                        System.out.println(KFile.fromJSONString(partRes[3]).getTree());
                    }
                }


            } catch (FileNotFoundException ex) {
                Logger.getLogger(KShell.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(KShell.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KShell.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(KShell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
