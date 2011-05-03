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
import com.kotak.client.model.AppData;
import com.kotak.client.view.KotakApp;

/**
 *
 * @author user
 */
public class Main {

    public static Daemon daemon;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Load Data
            AppData.instance = AppData.load();

            // Start App
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    KotakApp kotak = new KotakApp();
                    kotak.init();
                    kotak.setVisible(true);
                }
            });

            // Start Daemon
            daemon = new Daemon(AppData.instance.getEmail(), AppData.instance.getPassword());
            //daemon.start();

        } catch (FileNotFoundException ex) {
            try {
                AppData.instance = new AppData();
                AppData.save(AppData.instance);
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (InvalidClassException ex) {
            try {
                AppData.instance = new AppData();
                AppData.save(AppData.instance);
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
