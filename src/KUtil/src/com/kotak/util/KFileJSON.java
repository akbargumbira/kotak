package com.kotak.util;

import java.util.ArrayList;
import java.util.Date;

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
    protected ArrayList<KFileJSON> files = new ArrayList<KFileJSON>();
}
