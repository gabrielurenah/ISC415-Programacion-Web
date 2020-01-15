package edu.pucmm.sparkjdbc.Models;

public class Comment {
    private User author;
    private String comment;
    private String id;


    public Comment() {}

    public Comment(User author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
