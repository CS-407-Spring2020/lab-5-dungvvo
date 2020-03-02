package c.sakshi.lab5;

public class Note {

    private String date;
    private String username;
    private String title;
    private String content;

    public Note(String date, String username, String title, String content) {
        this.date = date;
        this.username = username;
        this.title = title;
        this.content = content;

    }

    public String getDate() {return this.date;}

    public String getUsername() {return this.username;}

    public String getTitle() {return this.title;}

    public String getContent() {return this.content;}
}
