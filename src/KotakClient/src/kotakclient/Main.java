/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakclient;

import kotakclient.view.KotakApp;

/**
 *
 * @author user
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Start App
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new KotakApp().setVisible(true);
//            }
//        });

        // Start Daemon
        (new Daemon()).start();
    }

}
