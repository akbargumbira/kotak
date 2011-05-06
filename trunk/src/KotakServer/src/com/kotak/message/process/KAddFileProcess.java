package com.kotak.message.process;

import com.kotak.message.model.KAddFile;
import com.kotak.message.model.KMessage;
import com.kotak.server.ServerData;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.server.database.QueryManagement;
import com.kotak.util.KFile;
import com.kotak.util.KFileSystem;
import com.kotak.util.KLogger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author user
 */
public class KAddFileProcess extends KMessageProcess {

    public KAddFileProcess(KMessage request) {
        super(request);
    }

    @Override
    public String  run() {
        try {
            response = "failed";
            // TODO AddFile
            // Get Message : addfile [email] [pass] [last_revision] [path] [content]#
            String email = request.getEmail();
            String pass = request.getPass();
            int last_revision = ((KAddFile) request).getClientLastRevision();
            String path = ((KAddFile) request).getFilePath();
            byte[] content = ((KAddFile) request).getFileContent();


            boolean isLocked = false;
            //Query Authenticate User, Last Revision and Last Revision Structure
            String queryAuthenticateUser = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + pass + "'";
            String queryLastRevStructure = "SELECT revision_repo.structure FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id "
                    + "WHERE user.email ='" + email + "' AND revision_repo.rev_num='" + last_revision + "'";
            String queryLastRev = "SELECT MAX(revision_repo.rev_num) FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id "
                    + "WHERE user.email ='" + email + "'";

            KLogger.writeln("queryLastRev : " + queryLastRev);

            //Authenticate User
            QueryManagement qM;
            StringBuilder sb = new StringBuilder();

            qM = new QueryManagement();
            ResultSet rs = qM.SELECT(queryAuthenticateUser);
            if (rs.next()) { //User is Authenticated
                if (!isLocked) { //Repository is not locked
                    //Check if revision is equal to last revision from database
                    //Get last revision :
                    ResultSet rsRev = qM.SELECT(queryLastRev);
                    rsRev.next();
                    int LasRev = Integer.parseInt(rsRev.getString("MAX(revision_repo.rev_num)")); //Last Revision
                    if (LasRev == last_revision) { //Last Revision equal to last revision
                        //Add/Update File  Process :
                        isLocked = true;

                        //Create folder r(last_revision+1) before add file
                        File folder = new File(ServerData.baseURL + "/" + email + "/r" + (last_revision + 1));
                        folder.mkdirs();

                        //Path to save file to server :
                        String strSavePath = ServerData.baseURL + "/" + email + "/r" + (last_revision + 1) + "/" + path;

                        //Save content as file to path in server :
                        KFileSystem.save(strSavePath, content);

                        //Change database structure
                        //Get last structure from database :
                        ResultSet rsLasRevStructure = qM.SELECT(queryLastRevStructure);
                        rsLasRevStructure.next();
                        String structure = rsLasRevStructure.getString("revision_repo.structure");

                        //Change structure to KFile :
                        KFile fileStructure = KFile.fromJSONString(structure);

                        //Find file to add in the last structure
                        KFile fileUpdated = fileStructure.findFile(path);
                        String structureUpdated;
                        
                        // Modified
                        Date modified = new Date(new File(ServerData.baseURL + "/" + email).lastModified());

                        //if file exist then it must be updated
                        // if file not exist then it must be added
                        if (fileUpdated != null) { //file update
                            //Set date file to last modified
                            fileUpdated.setModified(new Date(new File(strSavePath).lastModified()));

                            //Get structure update from new structure :
                            structureUpdated = KFile.toJSON(fileStructure);
                        } else { //new file must be added
                            
                            //Parse path to get fileName then Parent
                            String[] part = path.split("/");

                            //Get FileName :
                            String fileName = part[part.length - 1];

                            //Create new file named fileName 
                            KFile fileAdded = new KFile(fileName, modified, true);

                            //Add file logically in structure
                            String parentPath = KFile.getParentPath(path);
                            if (parentPath == null ? path == null : parentPath.equals(path)) {
                                fileStructure.addFile(fileAdded);
                            } else {
                                KFile parentFile = fileStructure.findFile(parentPath);
                                if (parentFile == null) {
                                    fileStructure.mkdirs(parentPath, fileAdded.getModified());
                                    parentFile = fileStructure.findFile(parentPath);
                                    parentFile.addFile(fileAdded);
                                }
                            }

                            //Update structure :
                            structureUpdated = KFile.toJSON(fileStructure);
                        }
                        //Update new structure to database :
                        //Get user_id :
                        String user_id = rs.getString("id");

                        //Query insert to table revision_repo :
                        String queryInsert = "INSERT INTO revision_repo (`user_id`,`rev_num`,`structure`) "
                                + "VALUES('" + user_id + "', '" + (last_revision + 1) + "', '" + structureUpdated + "')";

                        KLogger.writeln("query insert : " + queryInsert);

                        //Execute insert query :
                        if (qM.INSERT(queryInsert) == 1) { //insert is succesfull
                            sb.append("success ").append(last_revision + 1).append(" ").append(modified);
                            response = sb.toString();
                        } else { //insert is not succesfull
                            sb.append("failed add_failed");
                            response = sb.toString();
                        }
                        isLocked = false;
                    } else { //revision is not same with last revision in database
                        sb.append("failed revision_not_same");
                        response = sb.toString();
                    }
                } else { //repository is locked
                    sb.append("failed repository_is_locked");
                    response = sb.toString();
                }
            } else { //User is not authenticated
                sb.append("failed email_or_pass_is_wrong");
                response = sb.toString();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KAddFileProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KAddFileProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KAddFileProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
}
