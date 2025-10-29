package Student_Notes_Manager.src;

public class Note {
    private int id;
    private String title;
    private String content;
    private String created_At;
    private String updated_At;

    public Note(int id, String title, String content,String created_At,String updated_At) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created_At = created_At;
        this.updated_At = updated_At;
    }

    //Getters
    public int getId() {return id;} 
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getCreated_At() {return created_At;}
    public String getUpdated_At() {return updated_At;}

    //Setters
    public void setTitle(String title) {this.title = title; }
    public void setContent(String content) {this.content = content; }
    public void setCreated_At(String created_At) {this.created_At = created_At; }
    public void setUpdated_At(String updated_At) {this.updated_At = updated_At; }


    @Override
    public String toString() {
        String output =  "[" + id + "] " + title + "-" + content + 
        " ( Created : " + created_At + " )";

        if ( updated_At != null && updated_At != created_At) {
            output += " Updated : " + updated_At + " )";
        }
            return output;
    }

}
