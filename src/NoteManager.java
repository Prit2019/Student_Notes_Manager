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
        /* Let’s say a user types " my note " as a title — if you don’t trim it:

            The title looks like “my note”,

            But actually gets saved as " my note " (with invisible spaces).

            So later when you search for "my note", SQL doesn’t find it */


        if ( title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            System.out.println("Title or Content cannot be Empty.");
            return;
        }

        title = title.trim();
        content = content.trim();

        if(title.length() > 255) {
            System.out.println("Title too long!");
            return;
        }

        if(content.length() > 5000) {
            System.out.println("Content too long!");
            return;
        }


        String sql = "INSERT INTO notes (title, content, created_At, updated_At) VALUES (?,?, NOW(), NOW()) ";

        try(Connection conn = Database.connect();
        PreparedStatement stmnt = conn.prepareStatement(sql) ) {

            stmnt.setString(1, title.trim());
            stmnt.setString(2, content.trim());
            int rows = stmnt.executeUpdate();

            if (rows > 0) System.out.println("Note added successfully.");
            else System.out.println("Note not added!");


            

        } catch(SQLException e){
            System.out.println("Database Error : " + e.getMessage());
        }
    }


/* We want to sort the notes by their creation time and edited time and also let the user choose in which order he/she wants.
  either newestfirst or oldest first?
  
 */
    public static void viewNotes(boolean newestfirst) {

        String order = newestfirst ? "DESC" : "ASC" ;

        String sql = "SELECT * FROM notes ORDER BY updated_At DESC " + order; // ORDER BY sort the rows and DESC is just for Descending.

        try ( Connection conn = Database.connect();
              Statement stmnt = conn.createStatement();
              ResultSet rs = stmnt.executeQuery(sql)) {

                List<Note> notes = new ArrayList<>();

                while(rs.next()) {
                    Note n = new Note(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("created_At"),
                            rs.getString("updated_At")
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




    public static void updateNotes(int id) {

         


        String sql = "UPDATE notes SET title = ?, content = ?, updated_At = NOW() WHERE id = ?";

        try (Connection conn = Database.connect();
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter New Title : ");
                String newtitle = sc.nextLine();


                Scanner sc_1 = new Scanner(System.in);
                System.out.println("Enter New Content : ");
                String newcontent = sc_1.nextLine();


                  if ( newtitle == null || newtitle.trim().isEmpty() || newcontent == null || newcontent.trim().isEmpty()) {
            System.out.println("Title or Content cannot be Empty.");
            return;
        }


                stmnt.setString(1,newtitle.trim());
                stmnt.setString(2,newcontent.trim());
                stmnt.setInt(3,id);

                int rows = stmnt.executeUpdate();

                if(rows > 0) System.out.println("Notes Updated");
                else System.out.println("Notes with ID '" + id + "' not found!");
            }
            catch(SQLException e) {
                System.out.println("Database Error : " + e.getMessage());
            }

    }



    public static void search_Notes_by_Title(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Please enter a VALID Keyword!");
            return;
        }

        keyword = keyword.trim();

         // whitelisting : only updated_At allowed 
        String order = "updated_At";
        if( !order.equals("updated_At") && !order.equals("created_At")) order = "updated_At";

        String sql = "SELECT * FROM notes WHERE title LIKE ? ORDER BY " + order + " DESC";

        try(Connection conn = Database.connect();
            PreparedStatement stmnt = conn.prepareStatement(sql)) {

                stmnt.setString(1, "%" + keyword.trim() + "%"); // percentage meaning anything matching. like keyword is "java" then matching is "like java" and etc.
                ResultSet rs = stmnt.executeQuery();

                List<Note> results = new ArrayList<>();

                while(rs.next()) {

                    Note n = new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("created_At"),
                        rs.getString("updated_At")
                        );

                    results.add(n);
                }
                    
                    if(results.isEmpty()) System.out.println("No Notes found for : "+ keyword);
                    else System.out.println("\n Matching Notes : ");
                    results.forEach(System.out::println);
                


            } catch(SQLException e) {
                System.out.println("Search Error : "+ e.getMessage());
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



    //Advanced Search 
      /*Search by title keyword
        Search by date range (created or updated)
        Search by content keyword
        Combine them all dynamically
        So you could type like: Show me all notes updated between 2025-09-01 and 2025-09-30 that mention "exam". */

        public static void advancedSearch(String keyword, String startDate, String endDate, boolean searchContent) {

            StringBuilder sql = new StringBuilder("SELECT * FROM notes WHERE 1=1"); // stringbuilder to make it fast and save memory.

            List<Object> paramas = new ArrayList<>();

            if(keyword != null && !keyword.trim().isEmpty()) {  
                if(searchContent) {
                    sql.append("AND (title LIKE ? OR content LIKE ?)");
                    paramas.add("%" + keyword.trim() + "%"); // % cause of anything alike keyword
                    paramas.add("%" + keyword.trim() + "%");
                }
                else{
                    sql.append("AND title LIKE ?");
                    paramas.add("%" + keyword.trim() + "%");
                }

                if(startDate != null && startDate.trim().isEmpty()) {
                    sql.append(" AND DATE(updated_At) >= ?");
                    paramas.add(startDate.trim());
                }

                 if(endDate != null && endDate.trim().isEmpty()) {
                    sql.append(" AND DATE(updated_At) <= ?");
                    paramas.add(endDate.trim());
                }

                sql.append(" ORDER BY updated_At DESC");

                try( Connection conn = Database.connect();
                     PreparedStatement stmnt = conn.prepareStatement(sql.toString())) {

                        for(int i = 0; i < paramas.size(); i++) {
                            stmnt.setObject(i+1, paramas.get(i));
                        }

                        ResultSet rs = stmnt.executeQuery();

                        List<Note> results = new ArrayList<>();
                        while(rs.next()) {
                            Note n = new Note(
                                rs.getInt("id"), 
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getString("created_At"),
                                rs.getString("updated_At")
                            );

                            results.add(n);

                            if(results.isEmpty()){
                                System.out.println("No Notes found matching your filters!");
                            }
                            else{
                                System.out.println("Results found : ");
                                results.forEach(System.out::println);
                            }
                        }
                } catch(SQLException e) {
            System.out.println("Error in Search : "+ e.getMessage());
        }
        
            }
        } 
}