package com.kotak.message.process;
import com.google.gson.Gson;
import com.kotak.message.model.KCheck;
import com.kotak.message.model.KGetFile;
import com.kotak.message.model.KMessage;
import com.kotak.util.KFile;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.server.database.QueryManagement;
import com.kotak.server.FileSystemServer;
import java.lang.StringBuilder;

/**
 *
 * @author user
 */
public class KGetFileProcess extends KMessageProcess {

    public KGetFileProcess(KMessage request) {
        super(request);
    }

    @Override
    public String  run() {
        // TODO Run
        // Message Get: getfile [email] [password] [path_revision] [revision]
        String email = request.getEmail();
        String pass = request.getPass();
        String path = ((KGetFile)request).getFilePath();
        int revision = ((KGetFile)request).getFileRevision();
        
        //Query Authenticate User, Structure in revision :
        String queryAuthenticateUser = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
        String queryStructure = "SELECT revision_repo.structure FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id "
                + "WHERE user.email ='"+email+"' AND revision_repo.rev_num = '"+revision+"'";
       
        //Query management to handle query to database
        QueryManagement qM;
        StringBuilder sb = new StringBuilder();
        try {
            qM = new QueryManagement();

            //Execute query to authenticate user
            ResultSet rs = qM.SELECT(queryAuthenticateUser);
            if (rs.next()) { //user is authenticated
                //Get Structure  :
                //Execute query Structure :
                ResultSet rsStructure = qM.SELECT(queryStructure);
                if (rs.next()) { //structure is exist
                    //Get Structure field
                    String struct = rsStructure.getString("revision_repo.structure");

                    //Change structure to KFile :
                    KFile file = (KFile) (new Gson()).fromJson(struct, KFile.class);

                    //Check wheather requested file is exist in structure logically
                    if (file.isExist(path)) { //file is exist
                        //FileSystem Server to get Content
                        FileSystemServer fsServer = new FileSystemServer();
                        byte[] fileBytes = fsServer.getFileContent(email, revision, path);

                        if (fileBytes != null) { //file not null
                            sb.append("success").append(fileBytes);
                            response = sb.toString();
                        } else { //fie content is null
                            sb.append("failed filecontent_is_null");
                            response = sb.toString();
                        }
                    } else { //file is not exist
                        sb.append("failed no_such_requested_file");
                        response = sb.toString();
                    }
                } else { //structure is not exist
                    sb.append("failed no_such_structure");
                    response = sb.toString();
                }
            } else { //user is not authenticated
                sb.append("failed email_or_pass_is_wrong");
                response = sb.toString();
            }
        } catch (Exception ex) {
            Logger.getLogger(KCheckProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

}
