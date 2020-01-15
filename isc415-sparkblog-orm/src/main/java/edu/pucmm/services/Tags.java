package edu.pucmm.services;

import edu.pucmm.Models.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Tags extends DatabaseManagement<Tag> {

    private static Tags instance;

    private Tags() {
        super(Tag.class);
    }

    public static Tags getInstance() {
        return instance == null ? new Tags() : instance;
    }

    public Tag findByName(String tag) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select t from Tag t where t.tag = :tag");
        query.setParameter("tag", tag);
        List<Tag> list = query.getResultList();
        return list.size() > 0 ? list.get(0) : null;
    }
}
