package com.kotak.transferprotocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author user
 */
public class KTP {
    protected int status;
    protected Socket socket;
    protected InputStream in;
    protected OutputStream out;
}
