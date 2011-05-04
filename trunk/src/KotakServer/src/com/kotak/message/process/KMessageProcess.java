package com.kotak.message.process;

import com.kotak.message.model.KMessage;

/**
 *
 * @author user
 */
public abstract class KMessageProcess {
    protected KMessage request;
    protected String response;

    public KMessageProcess(KMessage request) {
        this.request = request;
    }

    public abstract String  run();
}
