package edu.pucmm.sparkjdbc.Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Article {
    private User author;
    private List<Comment> comments;
    private Timestamp date;
    private String id;
    private String information;
    private List<Tag> tags;
    private String title;

    public Article() {}

    public Article(User author, Timestamp date, String id, String information, List<Tag> tags, String title) {
        this.author = author;
        this.date = date;
        this.id = id;
        this.information = information;
        this.tags = tags;
        this.title = title;
        this.comments = new ArrayList<>();
    }

    public Article(User author, Timestamp date, String information, List<Tag> tags, String title) {
        this.author = author;
        this.date = date;
        this.information = information;
        this.tags = tags;
        this.title = title;
        this.comments = new ArrayList<>();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
