package com.kotak.client;

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
        // Start App
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                KApp kotak = new KApp();
                kotak.init();
                kotak.setVisible(true);
            }
        });

        // Start Shell
        new KShell().run();
    }
}
