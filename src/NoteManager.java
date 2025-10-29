package Student_Notes_Manager.src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class NoteManager {

    public static void addnote(String title,String content) {

        String sql = "INSERT INTO notes (title, content) VALUES (?,?)";

        try(Connection conn = Database.connect();
        PreparedStatement stmnt = conn.prepareStatement(sql) ) {

            stmnt.setString(1, title);
            stmnt.setString(2, content);
            stmnt.executeUpdate();

            System.out.println("Note added successfully.");


        } catch(SQLException e){
            e.printStackTrace();
        }
    }



    public static void viewNotes() {

        String sql = "SELECT * FROM notes";

        try ( Connection conn = Database.connect();
              Statement stmnt = conn.createStatement();
              ResultSet rs = stmnt.executeQuery(sql)) {

                List<Note> notes = new ArrayList<>();

                while(rs.next()) {
                    Note n = new Note(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content")
                         );

                notes.add(n);
                }
                if(notes.isEmpty()) {
                    System.out.println("Notes is Empty.");
                }else {
                    notes.forEach(System.out::println);
                }
                
              } catch(SQLException e) {
                e.printStackTrace();
              }
    }




    public static void updateNotes(int id, String title, String content) {

        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ?";

        try (Connection conn = Database.connect();
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter New Title : ");
                String newtitle = sc.nextLine();


                Scanner sc_1 = new Scanner(System.in);
                System.out.println("Enter New Content : ");
                String newcontent = sc_1.nextLine();


                stmnt.setString(1,newtitle);
                stmnt.setString(2,newcontent);
                stmnt.setInt(3,id);

                int rows = stmnt.executeUpdate();

                if(rows > 0) System.out.println("Notes Updated");
                else System.out.println("Notes with ID '" + id + "' not found!");
            }
            catch(SQLException e) {
                e.printStackTrace();
            }

    }




    public static void delete_Notes(int id) {

        String sql = "DELETE FROM notes WHERE id = ?";

        try(Connection conn = Database.connect();
            PreparedStatement stmnt = conn.prepareStatement(sql)) {

                stmnt.setInt(1, id);
                int rows = stmnt.executeUpdate();

                if(rows > 0 ) System.out.println("Notes Deleted!");
                else System.out.println("Notes with ID '" + id + "' not found!");

            }
            catch(SQLException e){
                e.printStackTrace();
            }

    }
}