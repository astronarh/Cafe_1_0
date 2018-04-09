package ru.astronarh.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.astronarh.model.Rating;

import java.util.List;

@Repository
public class RatingDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(Rating rating) {
        sessionFactory.getCurrentSession().save(rating);
        return rating.getId();
    }

    public Rating get(int id) {
        return sessionFactory.getCurrentSession().get(Rating.class, id);
    }

    public List<Rating> list() {
        Session session = sessionFactory.getCurrentSession();
        return (List<Rating>) session.createCriteria(Rating.class).list();
    }

    public void update(int id, Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        Rating rating2 = session.byId(Rating.class).load(id);
        rating2.setRestaurantId(rating.getRestaurantId());
        rating2.setRating(rating.getRating());
        session.flush();
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Rating rating = session.byId(Rating.class).load(id);
        session.delete(rating);
    }
}
