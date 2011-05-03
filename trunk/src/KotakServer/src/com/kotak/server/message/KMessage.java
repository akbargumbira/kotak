package com.kotak.server.message;

/**
 *
 * @author user
 */
public abstract class KMessage {
    protected String request;
    protected String response;

    public KMessage(String request) {
        this.request = request;
    }

    public abstract String  run();
}
