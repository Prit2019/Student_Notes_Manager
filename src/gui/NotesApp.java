package Student_Notes_Manager.src.gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Student_Notes_Manager.src.NoteManager;

public class NotesApp extends Application {
    @Override
    public void start(Stage stage) {
        TextField titleField = new TextField();
        TextArea contentArea = new TextArea();
        Button addBtn = new Button("Add Note");
        Button viewBtn = new Button("View Notes");
        TextArea output = new TextArea();
        output.setEditable(false);

        addBtn.setOnAction(e -> {
            NoteManager.addnote(titleField.getText(), contentArea.getText());
            titleField.clear();
            contentArea.clear();
        });

        viewBtn.setOnAction(e -> {
            output.clear();
            // You could make NoteManager.viewNotes return a List<String> instead of printing
            output.appendText("Feature coming soon: view notes in GUI!");
        });

        VBox root = new VBox(10,
                new Label("Title:"), titleField,
                new Label("Content:"), contentArea,
                new HBox(10, addBtn, viewBtn),
                output);
        root.setStyle("-fx-padding: 20;");

        stage.setTitle("Student Notes Manager");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

