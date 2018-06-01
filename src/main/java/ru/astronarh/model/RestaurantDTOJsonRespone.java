package ru.astronarh.model;

import ru.astronarh.dto.RestaurantDTO;

import java.util.Map;

public class RestaurantDTOJsonRespone {
    private RestaurantDTO restaurantDTO;
    private boolean validated;
    private Map<String, String> errorMessages;

    public RestaurantDTO getRestaurantDTO() {
        return restaurantDTO;
    }

    public void setRestaurantDTO(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "RestaurantDTOJsonRespone{" +
                "restaurantDTO=" + restaurantDTO +
                ", validated=" + validated +
                ", errorMessages=" + errorMessages +
                '}';
    }
}

