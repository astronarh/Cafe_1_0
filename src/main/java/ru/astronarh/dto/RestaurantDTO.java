package ru.astronarh.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class RestaurantDTO {
    private int id;

    @NotEmpty(message = "Enter name.")
    private String name;

    @NotEmpty(message = "Enter site.")
    private String site;

    @NotEmpty(message = "Enter descroption.")
    private String description;

    @NotEmpty(message = "Enter foto.")
    private String foto;

    private boolean enabled;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", description='" + description + '\'' +
                ", foto='" + foto + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
