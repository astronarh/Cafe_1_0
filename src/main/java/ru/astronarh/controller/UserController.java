package ru.astronarh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.astronarh.dto.UserDTO;
import ru.astronarh.model.*;
import ru.astronarh.service.RatingService;
import ru.astronarh.service.RestaurantService;
import ru.astronarh.service.UserRoleService;
import ru.astronarh.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/API")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RatingService ratingService;

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ModelAndView changeUserAccount(@ModelAttribute("user") UserDTO userDTO) {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        Map<String, String> errorList = Validators.emptyTestChangeUser(userDTO);
        if (errorList.size() == 0) {
            List<User> userList = userService.getAllUsers();
            boolean passwordChange = !userDTO.getPassword().equals(userService.getUser(userDTO.getId()).getPassword());
            userList.forEach(user -> {
                if (user.getId() == userDTO.getId()) return;
                if (user.getLogin().equals(userDTO.getLogin())) errorList.put("Username already exist", "Login");
                if (user.getEmail().equals(userDTO.getEmail())) errorList.put("Email already exist", "Email");
            });
            if (errorList.size() == 0) {
                User user = new User();
                user.setId(userDTO.getId());
                user.setLogin(userDTO.getLogin());
                user.setEmail(userDTO.getEmail());
                if (passwordChange) {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
                    user.setPassword(hashedPassword);
                } else {
                    user.setPassword(userDTO.getPassword());
                }
                user.setEnabled(userDTO.isEnabled() ? 1 : 0);
                userService.changeUser(user);
                if (userDTO.isAdmin()) {
                    UserRoles userRoles = new UserRoles();
                    userRoles.setLogin(userDTO.getLogin());
                    userRoles.setRole("ROLE_ADMIN");
                    userRoleService.addUserRole(userRoles);
                } else if (userRoleService.isAdmin(userService.getUser(userDTO.getId()).getLogin() )) {
                    userRoleService.deleteAdminUserRole(userDTO.getLogin());
                }
                model.addObject("message", "The account was created successfully.");
            } else {
                model.addObject("user", userDTO);
                model.addObject("message", errorList);
            }
        }else {
            model.addObject("user", userDTO);
            model.addObject("message", errorList);
        }
        List<Restaurant> restaurants = restaurantService.list();
        model.addObject("restaurants", restaurants);
        List<Rating> ratings = ratingService.list();
        model.addObject("ratings", ratings);
        List<User> users = userService.getAllUsers();
        model.addObject("users", users);
        return model;
    }

    /*@RequestMapping(value = "/saveUser/POST", method = RequestMethod.GET)
    @ResponseBody
    public String changeUserAccountPOST(String username) {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        System.out.println(username);
        return username;
    }*/

    @RequestMapping(value = "/saveUser/POST", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public UserDTOJsonRespone changeUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult result) {
        System.out.println(result);
        UserDTOJsonRespone respone = new UserDTOJsonRespone();
        if(result.hasErrors()){
            //Get error message
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );

            respone.setValidated(false);
            respone.setErrorMessages(errors);
        }else{
            // Implement business logic to save employee into database
            //..
            respone.setValidated(true);
            respone.setUserDTO(userDTO);
        }
        System.out.println(result);
        System.out.println(respone);
        return respone;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView user(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("user");
        User user = userService.getUser(id);
        model.addObject("user", user);
        model.addObject("admin", userRoleService.isAdmin(user.getLogin()));
        return model;
    }
}
