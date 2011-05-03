/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kotak.server.database;

import com.mysql.jdbc.Connection;
import java.sql.*;

/**
 *
 * @author user
 */
public class QueryManagement {
    private Connection conn;
    
    public QueryManagement() throws Exception {
        DatabaseHandler dH = new DatabaseHandler();
        conn = dH.getConnection();
    }
    
    public ResultSet SELECT(String query) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }
    
    public int INSERT(String query) throws SQLException {
        Statement statement = conn.createStatement();
        int res = statement.executeUpdate(query);
        return res;
    }
    
    public int UPDATE(String query) throws SQLException {
        Statement statement = conn.createStatement();
        int res = statement.executeUpdate(query);
        return res;
    }
    
    public int DELETE(String query) throws SQLException {
        Statement statement = conn.createStatement();
        int res = statement.executeUpdate(query);
        conn.close();
        return res;
    }
    
    public void CLOSECONNECTION() throws SQLException {
        conn.close();
    }
}
