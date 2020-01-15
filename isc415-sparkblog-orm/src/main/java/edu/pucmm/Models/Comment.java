package edu.pucmm.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Comment {
    @Id
    private String uid;
    private String comment;
    @OneToOne
    private User author;
    @ManyToOne
    private Article article;

    public Comment() { }

    public Comment(String uid, Article article, String comment, User author) {
        this.uid = uid;
        this.article = article;
        this.comment = comment;
        this.author = author;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
