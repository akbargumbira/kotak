/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kotakserver;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class DatabaseHandler {
    protected final String driver = "com.mysql.jdbc.Driver";
    protected final String dbName = "maninrelaychat";
    protected final String dbUserName = "root";
    protected final String dbPassword = "";

    protected Connection getConnection()
           throws  Exception{
            Class.forName(driver);
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, dbUserName, dbPassword);
        return connection;
    }

    public void putConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Enable to close DB connection");
                e.printStackTrace();
            }
        }
    }
    


}
