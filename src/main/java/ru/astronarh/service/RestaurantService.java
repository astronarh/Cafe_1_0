package ru.astronarh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astronarh.dao.RestaurantDao;
import ru.astronarh.model.Restaurant;

import java.util.List;

@Service("restaurantService")
public class RestaurantService {

    @Autowired
    RestaurantDao restaurantDao;

    @Transactional
    public int save(Restaurant restaurant) {
        return restaurantDao.save(restaurant);
    }

    @Transactional
    public Restaurant get(int id) {
        return restaurantDao.get(id);
    }

    @Transactional
    public List<Restaurant> list() {
        return restaurantDao.list();
    }

    @Transactional
    public void update(int id, Restaurant restaurant) {
        restaurantDao.update(id, restaurant);
    }

    @Transactional
    public void delete(int id) {
        restaurantDao.delete(id);
    }
}
