package ru.astronarh.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.astronarh.model.Cell;
import ru.astronarh.model.Restaurant;

import java.util.List;

@Repository
public class RestaurantDao {

    @Autowired
    private SessionFactory sessionFactory;


    public int save(Restaurant restaurant) {
        sessionFactory.getCurrentSession().save(restaurant);
        return restaurant.getId();
    }

    public Restaurant get(int id) {
        return (Restaurant) sessionFactory.getCurrentSession().get(Restaurant.class, id);
    }

    public List<Restaurant> list() {
        Session session = sessionFactory.getCurrentSession();
        /*CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Restaurant> cq = cb.createQuery(Restaurant.class);
        Root<Restaurant> root = cq.from(Restaurant.class);
        cq.select(root);
        Query<Restaurant> query = session.createQuery(cq);
        return query.getResultList();*/
        return (List<Restaurant>) session.createCriteria(Restaurant.class).list();
    }

    public void update(int id, Restaurant restaurant) {
        Session session = sessionFactory.getCurrentSession();
        Restaurant restaurant2 = (Restaurant) session.byId(Restaurant.class).load(id);
        restaurant2.setName(restaurant.getName());
        restaurant2.setSite(restaurant.getSite());
        restaurant2.setDescription(restaurant.getDescription());
        restaurant2.setFoto(restaurant.getFoto());
        restaurant2.setEnabled(restaurant.getEnabled());
        session.flush();
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Restaurant restaurant = (Restaurant) session.byId(Restaurant.class).load(id);
        session.delete(restaurant);
    }
}
