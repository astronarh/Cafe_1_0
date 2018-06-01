package ru.astronarh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.astronarh.dto.RestaurantDTO;
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

@RestController
@RequestMapping(value = "/adminAPI")
public class AdminController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RatingService ratingService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @RequestMapping("")
    public ModelAndView indexController() {
        ModelAndView model = new ModelAndView();
        model.setViewName("newAdmin");
        model.addObject("restaurant", new RestaurantDTO());
        model.addObject("userDTO", new UserDTO());
        return model;
    }

    @RequestMapping(path="/restaurants", method=RequestMethod.GET)
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.list();
    }

    @RequestMapping(path="/ratings", method=RequestMethod.GET)
    public List<Rating> getAllRatings(){
        return ratingService.list();
    }

    @RequestMapping(path="/users", method=RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/newRestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public RestaurantDTOJsonRespone saveRestaurant(@ModelAttribute @Valid RestaurantDTO restaurantDTO, BindingResult result) {

        RestaurantDTOJsonRespone respone = new RestaurantDTOJsonRespone();

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
            Restaurant restaurant = new Restaurant();
            restaurant.setName(restaurantDTO.getName());
            restaurant.setSite(restaurantDTO.getSite());
            restaurant.setDescription(restaurantDTO.getDescription());
            restaurant.setFoto(restaurantDTO.getFoto());
            restaurant.setEnabled(restaurantDTO.isEnabled() ? 1 : 0);
            if (restaurantDTO.getId() == 0) {
                restaurantService.save(restaurant);
            }
            else {
                restaurant.setId(restaurantDTO.getId());
                restaurantService.update(restaurantDTO.getId(), restaurant);
            }
            respone.setValidated(true);
            respone.setRestaurantDTO(restaurantDTO);
        }
        return respone;
    }

    @RequestMapping(value = "/editUser/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public UserDTOJsonRespone selectUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult result, @PathVariable int id) {
        UserDTOJsonRespone respone = new UserDTOJsonRespone();
        User user = userService.getUser(id);
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(user.getId());
        newUserDTO.setLogin(user.getLogin());
        newUserDTO.setEmail(user.getEmail());
        newUserDTO.setPassword(user.getPassword());
        newUserDTO.setEnabled(user.getEnabled() == 1);
        newUserDTO.setAdmin(userRoleService.isAdmin(user.getLogin()));
        respone.setUserDTO(newUserDTO);
        return respone;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public UserDTOJsonRespone editUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult result) {
        UserDTOJsonRespone respone = new UserDTOJsonRespone();

        if(result.hasErrors()){
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );

            respone.setValidated(false);
            respone.setErrorMessages(errors);
        }else{
            // Implement business logic to save employee into database
            //..

            System.out.println(userDTO);
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setEnabled(userDTO.isEnabled() ? 1 : 0);
            userService.changeUser(user);
            if (userDTO.isAdmin()) {
                if (!userRoleService.isAdmin(userDTO.getLogin())) {
                    UserRoles roles = new UserRoles();
                    roles.setLogin(userDTO.getLogin());
                    roles.setRole("ROLE_ADMIN");
                    userRoleService.addUserRole(roles);
                }
            } else {
                if (userRoleService.isAdmin(userDTO.getLogin())) {
                    userRoleService.deleteAdminUserRole(userDTO.getLogin());
                }
            }
            respone.setValidated(true);
        }
        return respone;
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/deleteRating/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void deleteRating(@PathVariable int id) {
        ratingService.delete(id);
    }

    @RequestMapping(value = "/editRestaurant/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public RestaurantDTOJsonRespone editRestaurant(@ModelAttribute @Valid RestaurantDTO restaurantDTO, BindingResult result, @PathVariable int id) {
        RestaurantDTOJsonRespone respone = new RestaurantDTOJsonRespone();
        Restaurant restaurant = restaurantService.get(id);
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setSite(restaurant.getSite());
        restaurantDTO.setDescription(restaurant.getDescription());
        restaurantDTO.setFoto(restaurant.getFoto());
        restaurantDTO.setEnabled(restaurant.getEnabled() == 1);
        respone.setRestaurantDTO(restaurantDTO);
        return respone;
    }

    @RequestMapping(value = "/deleteRestaurant/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void deleteRestaurant(@PathVariable int id) {
        restaurantService.delete(id);
    }
}
