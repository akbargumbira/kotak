/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kotak.util;

/**
 *
 * @author user
 */
public class KThread extends Thread {

    protected boolean loop = true;

    public void close() {
        loop = false;
    }
}
