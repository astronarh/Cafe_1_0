package ru.astronarh.model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="restaurants_id_seq")
    @SequenceGenerator(name="restaurants_id_seq", sequenceName="restaurants_id_seq", allocationSize=1)
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "site")
    private String site;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "foto")
    private String foto;

    @Basic
    @Column(name = "enabled")
    private int enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", description='" + description + '\'' +
                ", foto='" + foto + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
