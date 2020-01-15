package edu.pucmm.services;

import edu.pucmm.Models.Article;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Articles extends DatabaseManagement<Article> {

    private static Articles instance;

    private Articles() {
        super(Article.class);
    }

    public static Articles getInstance() {
        return instance == null ? new Articles() : instance;
    }

    public List<Article> lazyFind(int pageNumber) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select a from Article a order by date DESC", Article.class);
        int pageSize = 5;
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Article> articlesList = query.getResultList();
        for (Article article: articlesList) {
            if (article.getInformation().length() > 70) {
                article.setInformation(article.getInformation().substring(0,70) + "...");
            }
        }
        return articlesList;
    }
}
