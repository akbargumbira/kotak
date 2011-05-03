package kotakserver.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * File or Folder
 * @author user
 */
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
}
