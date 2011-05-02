package kotakclient.model;

import java.util.ArrayList;
import java.util.Date;

public class KFile {
    private String name;
    private Date modified;
    private ArrayList<KFile> files = new ArrayList<KFile>();

    public KFile(String name, Date modified) {
        this.name = name;
        this.modified = modified;
    }

    public void AddFile(KFile file) {
        files.add(file);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }
}