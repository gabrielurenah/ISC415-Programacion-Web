package edu.pucmm.services;

import edu.pucmm.Models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Comments extends DatabaseManagement<Comment> {

    private static Comments instance;

    private Comments() {
        super(Comment.class);
    }

    public static Comments getInstance() {
        return instance == null ? new Comments() : instance;
    }

    public List<Comment> findAllByArticleUid(String uid) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select c from Comment c where c.article.id = :uid");
        query.setParameter("uid", uid);
        List<Comment> list = query.getResultList();
        return list;
    }
}
