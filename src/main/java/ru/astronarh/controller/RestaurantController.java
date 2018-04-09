package ru.astronarh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.astronarh.model.Rating;
import ru.astronarh.model.Restaurant;
import ru.astronarh.service.RatingService;
import ru.astronarh.service.RestaurantService;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RatingService ratingService;

    @RequestMapping("/admin")
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        List<Restaurant> restaurants = restaurantService.list();
        model.addObject("restaurants", restaurants);
        return model;
    }

    @RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("restauraunt") Restaurant restaurant) {
        ModelAndView model = new ModelAndView();
        try {
            String newString = new String(restaurant.getName().getBytes("ISO-8859-1"),"UTF-8");
            restaurant.setName(newString);
            newString = new String(restaurant.getDescription().getBytes("ISO-8859-1"),"UTF-8");
            restaurant.setDescription(newString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        restaurantService.save(restaurant);
        model.setViewName("/admin");
        return model;
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
    public String saveRating(int userId, int restaurantId, int rating) {
        Rating newRating = new Rating();
        newRating.setUserID(userId);
        newRating.setRestaurantId(restaurantId);
        newRating.setRating(rating);
        ratingService.save(newRating);
        System.out.println(newRating);
        return "Saved rating: " + rating;
    }

    @PostMapping("/upload-photo")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        //storageService.store(file);
        //redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
