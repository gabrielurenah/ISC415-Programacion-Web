package edu.pucmm.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Article implements Serializable {
    @Id
    private String uid;
    private String title;
    private String information;
    @ManyToOne
    private User author;
    private Timestamp date;
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    private Set<Comment> comments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ARTICLE_TAG", joinColumns = @JoinColumn(name = "ARTICLES_UID"), inverseJoinColumns = @JoinColumn(name = "TAGS_UID"))
    private Set<Tag> tags;
    @OneToMany(mappedBy = "recommendationId.article", fetch = FetchType.EAGER)
    private Set<Recommendation> recommendations;

    public Article() { }

    public Article(String uid, String title, String information, User author, Timestamp date, Set<Tag> tags) {
        this.uid = uid;
        this.title = title;
        this.information = information;
        this.author = author;
        this.date = date;
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
