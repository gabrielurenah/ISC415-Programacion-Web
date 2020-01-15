package edu.pucmm.services;

import edu.pucmm.Models.Article;
import edu.pucmm.Models.Recommendation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Recommendations extends DatabaseManagement<Recommendation> {

    private static Recommendations instance;

    private Recommendations() {
        super(Recommendation.class);
    }

    public static Recommendations getInstance() {
        return instance == null ? new Recommendations() : instance;
    }

    public int numberOfRecommendations(Article article, Boolean criteria) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select r from Recommendation r where r.recommendationId.article = :article and r.isLike = :criteria");
        query.setParameter("article", article);
        query.setParameter("criteria", criteria);
        List<edu.pucmm.Models.Recommendation> recommendations = query.getResultList();
        return recommendations.size();
    }
}
