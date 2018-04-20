package ru.astronarh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.astronarh.model.Rating;
import ru.astronarh.model.Restaurant;
import ru.astronarh.model.User;
import ru.astronarh.service.RatingService;
import ru.astronarh.service.RestaurantService;
import ru.astronarh.service.UserService;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Controller
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RatingService ratingService;

    @Autowired
    UserService userService;

    @RequestMapping("/admin")
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        List<Restaurant> restaurants = restaurantService.list();
        model.addObject("restaurants", restaurants);
        List<Rating> ratings = ratingService.list();
        model.addObject("ratings", ratings);
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
    public ModelAndView main(){
        ModelAndView model = new ModelAndView();
        model.addObject("restaurants", restaurantService.list());
        model.setViewName("/index");
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
    public ModelAndView getUser(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        model.addObject("user", "1");
        //model.setViewName("/admin");
        return model;
    }
}
