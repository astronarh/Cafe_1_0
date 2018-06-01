package ru.astronarh.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import ru.astronarh.dto.RestaurantDTO;
import ru.astronarh.model.Rating;
import ru.astronarh.model.Restaurant;
import ru.astronarh.model.User;
import ru.astronarh.service.RatingService;
import ru.astronarh.service.RestaurantService;
import ru.astronarh.service.UserRoleService;
import ru.astronarh.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@RestController
public class RestaurantController {

    //private static Log log = LogFactory.getLog(RestaurantController.class);

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RatingService ratingService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @RequestMapping("/admin")
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        List<Restaurant> restaurants = restaurantService.list();
        model.addObject("restaurants", restaurants);
        List<Rating> ratings = ratingService.list();
        model.addObject("ratings", ratings);
        List<User> users = userService.getAllUsers();
        model.addObject("users", users);
        return model;
    }

    @RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("restauraunt") Restaurant restaurant) {
        ModelAndView model = new ModelAndView();
        if (restaurant.getId() == 0) {
            convert(restaurant);
            restaurantService.save(restaurant);
        } else {
            convert(restaurant);
            restaurantService.update(restaurant.getId(), restaurant);
        }
        List<Restaurant> restaurants = restaurantService.list();
        model.addObject("restaurants", restaurants);
        List<Rating> ratings = ratingService.list();
        model.addObject("ratings", ratings);
        model.setViewName("/admin");
        return model;
    }

    private void convert(@ModelAttribute("restauraunt") Restaurant restaurant) {
        try {
            String newString = new String(restaurant.getName().getBytes("ISO-8859-1"),"UTF-8");
            restaurant.setName(newString);
            newString = new String(restaurant.getDescription().getBytes("ISO-8859-1"),"UTF-8");
            restaurant.setDescription(newString);
            restaurant.setEnabled(1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(Locale locale){
        ModelAndView model = new ModelAndView();
        model.addObject("restaurants", restaurantService.list());
        model.setViewName("/index");

        /*String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) username = ((UserDetails)principal).getUsername();
        else username = principal.toString();
        log.info("load " + model.getViewName() + " [" + username + "]");*/

        return model;
    }

    @RequestMapping(value="/rating", method=RequestMethod.GET)
    @ResponseBody
    public String saveRating(int restaurantId, int rating) {
        List<Rating> ratings = ratingService.list();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Rating newRating = new Rating();
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        List<User> users = userService.getAllUsers();
        int userId = 0;
        for(User x : users) { if(x.getLogin().equals(username)) userId = x.getId(); }
        int finalUserId = userId;
        List<Rating> thisUserRatings = ratings.stream().filter(x -> x.getUserID() == finalUserId).collect(Collectors.toList());
        AtomicBoolean testified = new AtomicBoolean(false);
        //thisUserRatings.forEach(x -> {if (now.getTime() - x.getDate().getTime() > 86400000) testified.set(true); });
        if (thisUserRatings.size() != 0) {
            if ((now.getTime() - thisUserRatings.get(thisUserRatings.size() - 1).getDate().getTime() > 86400000)) testified.set(true);
        } else {
            testified.set(true);
        }
        if(testified.get()) {
            newRating.setUserID(userId);
            newRating.setRestaurantId(restaurantId);
            newRating.setRating(rating);
            newRating.setDate(now);
            ratingService.save(newRating);
        }
        return "Saved rating: " + rating;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView editRestaurant(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("edit");
        Restaurant restaurant = restaurantService.get(id);
        model.addObject("restaurant", restaurant);
        List<Rating> ratings = ratingService.list();
        model.addObject("ratings", ratings);
        return model;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView deleteRestaurant(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        restaurantService.delete(id);
        List<Restaurant> restaurants = restaurantService.list();
        model.addObject("restaurants", restaurants);
        List<Rating> ratings = ratingService.list();
        model.addObject("ratings", ratings);
        return model;
    }

    @RequestMapping(value = "/rating/delete/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView deleteRating(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        ratingService.delete(id);
        List<Restaurant> restaurants = restaurantService.list();
        model.addObject("restaurants", restaurants);
        List<Rating> ratings = ratingService.list();
        model.addObject("ratings", ratings);
        return model;
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

    @RequestMapping(value = "/restaurant", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView restaurant() {
        ModelAndView model = new ModelAndView();
        model.setViewName("restaurant");
        model.addObject("restaurant", new RestaurantDTO());
        return model;
    }

    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public RestaurantDTO editRestaurantPOST(@PathVariable int id, RestaurantDTO restaurantDTO) {
        System.out.println(restaurantDTO);
        ModelAndView model = new ModelAndView();
        model.setViewName("restaurant");
        Restaurant restaurant = restaurantService.get(id);
        model.addObject("restaurant", restaurant);
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setSite(restaurant.getSite());
        restaurantDTO.setDescription(restaurant.getDescription());
        restaurantDTO.setFoto(restaurant.getFoto());
        restaurantDTO.setEnabled(restaurant.getEnabled() == 1);
        return restaurantDTO;
    }

}
