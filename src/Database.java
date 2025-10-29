package Student_Notes_Manager.src;

import java.sql.*;


public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/notesdb";
    private static final String USER = "root";
    private static final String PASSWORD ="PRITPhysics2019@";

    public static Connection connect() {
        try{ 
            DriverManager.getConnection(URL,USER,PASSWORD);
        } catch(SQLException e) {
            System.out.println("Database Connection Failed! : " + e.getMessage());
        
        }
        return null;
    }


    public static void createTable() {
        try( Connection conn = connect(); Statement stmnt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS notes (" + 
                         "id INT AUTO_INCREMENT PRIMARY KEY; " +
                         "title VARCHAR(225); " +
                         "content TEXT); " +
                        "ALTER TABLE notes ADD COLUMN created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP " ;

                         
            stmnt.executeUpdate(sql);
            System.out.println("Table 'notes' is ready.");


        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
