package ru.astronarh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astronarh.dao.RatingDao;
import ru.astronarh.model.Rating;

import java.util.List;

@Service("ratingService")
public class RatingService {

    @Autowired
    RatingDao ratingDao;

    @Transactional
    public int save(Rating rating) {
        return ratingDao.save(rating);
    }

    @Transactional
    public Rating get(int id) {
        return ratingDao.get(id);
    }

    @Transactional
    public List<Rating> list() {
        return ratingDao.list();
    }

    @Transactional
    public void update(int id, Rating rating) {
        ratingDao.update(id, rating);
    }

    @Transactional
    public void delete(int id) {
        ratingDao.delete(id);
    }
}
