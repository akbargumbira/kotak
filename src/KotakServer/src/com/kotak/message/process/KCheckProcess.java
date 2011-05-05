/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kotak.message.process;

import com.kotak.message.model.KCheck;
import com.kotak.message.model.KMessage;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kotak.server.database.QueryManagement;

/**
 *
 * @author user
 */
public class KCheckProcess extends KMessageProcess {

    public KCheckProcess(KMessage request) {
        super(request);
    }

    @Override
    public String  run() {
        // TODO run
        // Get message : check [email] [pass] [client_revision]
        String email = request.getEmail();
        String pass = request.getPass();
        int revision = ((KCheck)request).getClientLastRevision();
        
        //Query Authenticate User & Last Revision
        String queryAuthUser = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+pass+ "'";
        String queryLastRev = "SELECT MAX(revision_repo.rev_num) FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id "
                + "WHERE user.email ='"+email+"'";
      
        //Authenticate User
        QueryManagement qM;
        StringBuilder sb = new StringBuilder();
        try {
            qM = new QueryManagement();
            ResultSet rs = qM.SELECT(queryAuthUser);
            if (rs.next()) { //User is Authenticated
                //Get Last Revision from user
                ResultSet rsRev = qM.SELECT(queryLastRev);
                rsRev.next();
                int LasRev = Integer.parseInt(rsRev.getString("MAX(revision_repo.rev_num)"));

                if (LasRev == revision) { // revision equal to Last Revision
                    sb.append("success nochange");
                    response = sb.toString();
                } else { // revision not equal to last revision, client need to update
                    //Query Last Revision Structure
                    String queryLastRevStructure = "SELECT revision_repo.structure FROM user LEFT JOIN revision_repo ON user.id=revision_repo.user_id "
                            + "WHERE user.email ='" + email + "' AND revision_repo.rev_num = '" + LasRev + "'";
                    
                    //Get Query Result
                    ResultSet rsStructure = qM.SELECT(queryLastRevStructure);
                    rsStructure.next();
                    String structure = rsStructure.getString("revision_repo.structure");
                    
                    sb.append("success structure ").append(LasRev).append(" ").append(structure);
                    response = sb.toString();
                }
            } else {
                sb.append("failed email_or_pass_is_wrong");
                response = sb.toString();
            }
        } catch (Exception ex) {
            Logger.getLogger(KCheckProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
