package com.kotak.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class KFileJSON {
    protected String name;
    protected Date lastModified;
    
    /**
     * This object is a folder if size of <i>files</i> > 0
     */
    protected LinkedList<KFileJSON> files = new LinkedList<KFileJSON>();
}
