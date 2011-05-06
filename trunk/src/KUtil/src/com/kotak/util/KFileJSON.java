package com.kotak.util;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class KFileJSON {
    protected String name;
    protected Date lastModified;
    protected boolean isFile = true;
    
    /**
     * This object is a folder if size of <i>files</i> > 0
     */
    protected LinkedList<KFileJSON> files = new LinkedList<KFileJSON>();
    
    public String getTree() {
        return getTreeRec(0);
    }
    
    private String getTreeRec(int space) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<space; ++i) {
            sb.append(" ");
        }
        
        sb.append(name).append(" ").append(lastModified);
        
        for (int i=0; i < files.size(); ++i) {
            sb.append("\n").append(files.get(i).getTreeRec(space + 1));
        }
        
        return sb.toString();
    }
}
