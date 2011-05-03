/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.client.model.KAppData;
import com.kotak.client.view.KApp;

/**
 *
 * @author user
 */
public class Main {

    public static KDaemon daemon;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Load Data
            KAppData.instance = KAppData.load();

            // Start App
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    KApp kotak = new KApp();
                    kotak.init();
                    kotak.setVisible(true);
                }
            });

            // Start KDaemon
            daemon = new KDaemon(KAppData.instance.getEmail(), KAppData.instance.getPassword());
            //daemon.start();

        } catch (FileNotFoundException ex) {
            try {
                KAppData.instance = new KAppData();
                KAppData.save(KAppData.instance);
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (InvalidClassException ex) {
            try {
                KAppData.instance = new KAppData();
                KAppData.save(KAppData.instance);
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
