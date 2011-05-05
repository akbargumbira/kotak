package com.kotak.message.process;

import com.kotak.message.model.KDelete;
import com.kotak.message.model.KMessage;
import com.kotak.server.ServerData;
import com.kotak.server.database.QueryManagement;
import com.kotak.util.KFile;
import com.kotak.util.KFileSystem;
import java.io.File;
import java.sql.ResultSet;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class KDeleteProcess extends KMessageProcess {

    public KDeleteProcess(KMessage request) {
        super(request);
    }

    @Override
    public String run() {
            response = "failed";
            // TODO Run
            // Get message delete [email] [pass] [path] [client_last_revision]
            String email = request.getEmail();
            String pass = request.getPass();
            String path = ((KDelete)request).getFilePath();
            int last_revision = ((KDelete)request).getClientLastRevision();
            
            boolean isLocked = false;
            //String QueryAuthenticateUser, Query Last Revision Structure, and Query Last Revision
            String queryAuthenticateUser = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
            String queryLastRevStructure = "SELECT revision_repo.structure FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id"
                    + "WHERE user.email ='"+email+"' AND revision_repo.rev_num='"+last_revision+"' ";
            String queryLastRev = "SELECT MAX(revision_repo.rev_num) FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id"
                    + "WHERE user.email ='"+email+"'";

            //Query Management to handle query to database :
            QueryManagement qM;
            StringBuilder sb = new StringBuilder();
            try {
                qM = new QueryManagement();
                
                //Authenticate User : 
                ResultSet rs = qM.SELECT(queryAuthenticateUser);
                if (rs.next()) { // User is authenticated
                    //Check whether repository is locked or not
                    if (!isLocked) { // repository is not locked
                        // Query Last Revision from database :
                        ResultSet rsRev = qM.SELECT(queryLastRev);
                        int LasRev = Integer.parseInt(rsRev.getString("MAX(revision_repo.rev_num)"));
                        
                        // Check whether revision is equal to last_revision in database or not :
                        if (LasRev == last_revision) { // Last Revision is equal to last revision from database
                            //Delete File Process :
                            isLocked = true;

                            //Create folder r(last_revision+1) before delete file
                            //Delete file means that we have to create new revision in server
                            File folder = new File(ServerData.baseURL + "/" + email + "/r" + (last_revision + 1));
                            folder.mkdir();
                            
                            //Change database structure :
                            //Get Last Revision Structure
                            ResultSet rsLastRevStructure = qM.SELECT(queryLastRevStructure);
                            String structure = rsLastRevStructure.getString("revision_repo.structure");
                            
                            //Change structure to KFile
                            KFile fileStructure = KFile.fromJSONString(structure);
                            
                            //Delete file in filestructure :
                            KFile fileDeleted = fileStructure.findFile(path);
                            if (fileDeleted != null) { //fileDeleted is exist
                                fileStructure.removeFile(path, new Date(new File(path).lastModified()));
                                
                                //Change new filestructure to JSON :
                                structure = KFile.toJSON(fileStructure);
                                
                                //Update database :
                                //Get user_id
                                String user_id = rs.getString("id");
                                
                                //Query insert new revision :
                                String queryInsert = "INSERT INTO revision_repo ('user_id','rev_num','structure')"
                                        + "VALUES (' '" + user_id + "' ',' '" + (last_revision + 1) + "' ',' '" + structure + "' ')";
                                
                                //Execute Query
                                if (qM.INSERT(queryInsert) == 0) { //Insert is sucessfull
                                    sb.append("success ").append(last_revision + 1);
                                    response = sb.toString();
                                } else { //Insert is not sucessfull
                                    sb.append("failed add_failed");
                                    response = sb.toString();
                                }
                            } else { //FIle deleted is not exist
                                sb.append("failed file_requested_to_delete_not_exist");
                                response = sb.toString();
                            }
                            isLocked = false;
                        } else {
                            sb.append("failed revision_not_same");
                            response = sb.toString();
                        }
                    } else { // repository is locked
                        sb.append("failed repository_is_locked");
                        response = sb.toString();
                    }
                } else { // User is not authenticated
                    sb.append("failed email_or_pass_is_wrong");
                    response = sb.toString();
                }
        } catch (Exception ex) {
            Logger.getLogger(KDeleteProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return response;
    }
}
