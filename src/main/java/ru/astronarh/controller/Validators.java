package ru.astronarh.controller;

import ru.astronarh.dto.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Validators {

    public static List<String> emptyTest(UserDTO userDTO) {
        List<String> errorList = new ArrayList<>();
        if (userDTO.getLogin().equals("")) errorList.add("Username is empty");
        if (userDTO.getEmail().equals("")) errorList.add("Email is empty");
        if (userDTO.getPassword().equals("")) errorList.add("Password is empty");
        if (userDTO.getMatchingPassword().equals("")) errorList.add("Confirm password is empty");
        if (!userDTO.getMatchingPassword().equals(userDTO.getPassword())) errorList.add("Passwords do not match");
        if (!userDTO.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$")) errorList.add("Email is wrong");
        return errorList;
    }

    public static Map<String, String> emptyTestChangeUser(UserDTO userDTO) {
        Map<String, String> errorMap = new HashMap<>();
        if (userDTO.getLogin().equals("")) errorMap.put("Username is empty", "Login");
        if (userDTO.getEmail().equals("")) errorMap.put("Email is empty", "Email");
        if (userDTO.getPassword().equals("")) errorMap.put("Password is empty", "Password");
        if (userDTO.getMatchingPassword().equals("")) errorMap.put("Confirm password is empty", "Matching Password");
        if (!userDTO.getMatchingPassword().equals(userDTO.getPassword())) errorMap.put("Passwords do not match", "Matching Password");
        if (!userDTO.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$")) errorMap.put("Email is wrong", "Email");
        return errorMap;
    }
}
