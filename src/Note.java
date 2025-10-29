package Student_Notes_Manager.src;

public class Note {
    private int id;
    private String title;
    private String content;


    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }

    //Getters
    public int getId() {return id;} 
    public String getTitle() {return title;}
    public String getContent() {return content;}

    //Setters
    public void setTitle(String title) {this.title = title; }
    public void setContent(String content) {this.content = content; }

    @Override
    public String toString() {
        return "[" + id + "] " + title + "-" + content;
    }

}
