package ru.astronarh.model;

import ru.astronarh.dto.UserDTO;

import java.util.Map;

public class UserDTOJsonRespone {
    private UserDTO userDTO;
    private boolean validated;
    private Map<String, String> errorMessages;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
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
        return "UserDTOJsonRespone{" +
                "userDTO=" + userDTO +
                ", validated=" + validated +
                ", errorMessages=" + errorMessages +
                '}';
    }
}
