/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakclient;

/**
 *
 * @author user
 */
public class MyThread extends Thread {

    protected boolean loop = true;

    public void close() {
        loop = false;
    }
}
