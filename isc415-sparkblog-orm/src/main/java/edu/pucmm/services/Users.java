package edu.pucmm.services;

import edu.pucmm.Models.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Users extends DatabaseManagement<User> {
    private static Users instance;

    private Users() {
        super(User.class);
    }

    public static Users getInstance() {
        return instance == null ? new Users() : instance;
    }

    public User validateCredentials(String username, String password) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from User u where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> list = query.getResultList();
        return list.size() > 0 ? list.get(0) : null;
    }

    public User findByObject(User user) {
        if (user != null) {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("select u from User u where u.uid = :uid");
            query.setParameter("uid", user.getUid());
            List<User> list = query.getResultList();
            return list.size() > 0 ? list.get(0) : null;
        } else return null;
    }
}
