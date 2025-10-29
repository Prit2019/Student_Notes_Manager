package Student_Notes_Manager.src;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Database.createTable();
        Scanner sc = new Scanner(System.in);

        while (true) { 
            System.out.println("\n--- Student-Notes-Manager ---");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Update Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Search Note by Title");
            System.out.println("6. Exit");
            System.out.print("Choose : ");

            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Title : ");
                    String title = sc.nextLine();
                    System.out.print("Enter Content : ");
                    String content = sc.nextLine();

                    NoteManager.addnote(title, content);
                    break;

                case 2:
                    System.out.println("View Notes sorted by : ");
                    System.out.println("1. Newest First ");
                    System.out.println("2. Oldest First ");
                    sc.nextInt();
                    sc.nextLine();

                    NoteManager.viewNotes(choice == 1);

                    break;

                case 3:
                    System.out.println("Enter Note ID to Update : ");
                    int updateID = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.println("Enter new Title : ");
                    String newTitle = sc.nextLine();
                    System.out.println("Enter new Content : ");
                    String newContent = sc.nextLine();
                    
                    NoteManager.updateNotes(updateID);    
                    break;

                case 4:
                    System.out.println("Enter Note ID to Delete : ");
                    int deleteID = sc.nextInt();
                    sc.nextLine();
                    NoteManager.delete_Notes(deleteID);
                    break;

                case 5:
                    System.out.println("Enter Keyword to Search : ");
                    String keyword = sc.nextLine();
                    NoteManager.search_Notes_by_Title(keyword);
                    break;


                case 6:
                    System.out.println("Goodbye!");
                    return;
                    
                default : 
                    System.out.println("Invalid Choice!");

            }
        }
    }
}
