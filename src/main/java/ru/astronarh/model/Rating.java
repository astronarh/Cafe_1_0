package ru.astronarh.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ratings_id_seq")
    @SequenceGenerator(name="ratings_id_seq", sequenceName="ratings_id_seq", allocationSize=1)
    private int id;

    @Basic
    @Column(name = "user_id")
    private int userID;

    @Basic
    @Column(name = "restaurant_id")
    private int restaurantId;

    @Basic
    @Column(name = "rating")
    private int rating;

    @Basic
    @Column(name = "date")
    private Timestamp date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", userID=" + userID +
                ", restaurantId=" + restaurantId +
                ", rating=" + rating +
                ", date=" + date +
                '}';
    }
}
