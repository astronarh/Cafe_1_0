package ru.astronarh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ru.astronarh.dto.UserDTO;
import ru.astronarh.model.*;
import ru.astronarh.service.CellService;
import ru.astronarh.service.CrosswordService;
import ru.astronarh.service.UserRoleService;
import ru.astronarh.service.UserService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CrosswordController {

    @Autowired
    CellService cellService;

    @Autowired
    CrosswordService crosswordService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    @RequestMapping("/")
    public String indexController() {
        return "index";
    }

    @RequestMapping("/aboutUser")
    public String aboutUserController() {
        return "aboutUser";
    }

    @RequestMapping("/about")
    public String aboutController() {
        return "about";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    /*@RequestMapping("/registration")
    public String registrationPage() {
        return "registration";
    }*/

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginController(@ModelAttribute(value = "user")String user, @ModelAttribute(value = "login")String login) {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        System.out.println(user + " " + login);
        return model;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createController(@RequestParam(value = "column", required = false) String column, @RequestParam(value = "lines", required = false) String lines) {
        ModelAndView model = new ModelAndView();
        model.setViewName("create");
        List<Integer> horizontal = IntStream.range(1, 20).boxed().collect(Collectors.toList());
        List<Integer> vertical = IntStream.range(1, 20).boxed().collect(Collectors.toList());
        model.addObject("horizontal", horizontal);
        model.addObject("vertical", vertical);
        if (column != null && lines != null) {
            model.addObject("column", IntStream.range(0, Integer.parseInt(column)).boxed().collect(Collectors.toList()));
            model.addObject("lines", IntStream.range(0, Integer.parseInt(lines)).boxed().collect(Collectors.toList()));
        }
        return model;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveController(@ModelAttribute(value = "cells")Cells cells, @ModelAttribute(value = "size")String size) {
        ModelAndView model = new ModelAndView();
        model.setViewName("create");
        List<Integer> horizontal = IntStream.range(1, 20).boxed().collect(Collectors.toList());
        List<Integer> vertical = IntStream.range(1, 20).boxed().collect(Collectors.toList());
        model.addObject("horizontal", horizontal);
        model.addObject("vertical", vertical);
        int rows = Integer.parseInt(size.split(" ")[1]);
        int columns = Integer.parseInt(size.split(" ")[0]);
        int lasId = 0;
        try { lasId = cellService.lastId(); } catch (Exception e) { e.fillInStackTrace(); }
        Crossword crossword = new Crossword(rows, columns, lasId + 1, lasId + (rows * columns));
        crossword.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        crosswordService.addCrossword(crossword);
        cells.getCells().forEach(x -> cellService.addCell(x));
        return model;
    }

    @RequestMapping(value = "/crossword/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView loadCrossword(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("crossword");
        Crossword crossword = crosswordService.getCrossword(id);
        List<Cell> allCell = cellService.getAllCell();
        //List<Cell> cellListList = allCell.stream().skip(crossword.getIdBegin() - 1).limit(crossword.getIdEnd()).collect(Collectors.toList());
        List<Cell> cellListList = new ArrayList<>();
        allCell.forEach(x -> { if (x.getId() >= crossword.getIdBegin() && x.getId() <= crossword.getIdEnd()) cellListList.add(x); });
        Cell[][] cells = new Cell[crossword.getRows()][crossword.getColumns()];
        int counter = 0;
        for (int i = 0; i < crossword.getRows(); i++) {
            for (int j = 0; j < crossword.getColumns(); j++) {
                cells[i][j] = cellListList.get(counter++);
            }
        }
        model.addObject("cells", cells);
        model.addObject("cellList", cellListList);
        model.addObject("crossword", crossword);
        return model;
    }

    @RequestMapping("/crossword")
    public ModelAndView crosswordController(@RequestParam(required = false) Integer page) {
        ModelAndView model = new ModelAndView();
        model.setViewName("crossword");
        List<Crossword> crosswordList = crosswordService.getAllCrossword();
        //model.addObject("crosswordList", crosswordList);

        PagedListHolder<Crossword> pagedListHolder = new PagedListHolder<>(crosswordList);
        pagedListHolder.setPageSize(10);
        model.addObject("maxPages", pagedListHolder.getPageCount());
        if(page == null || page < 1 || page > pagedListHolder.getPageCount()) page = 1;
        model.addObject("page", page);
        if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
            pagedListHolder.setPage(0);
            model.addObject("crosswordList", pagedListHolder.getPageList());
        }
        else if(page <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(page-1);
            model.addObject("crosswordList", pagedListHolder.getPageList());
        }

        return model;
    }

    @RequestMapping(value = "/crosswordDelete/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView crosswordDelete(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/crossword");
        Crossword crossword = crosswordService.getCrossword(id);
        for (int i = crossword.getIdBegin(); i <= crossword.getIdEnd(); i++) cellService.deleteCell(i);
        crosswordService.deleteCrossword(id);
        return model;
    }

    /*@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "registration";
    }*/

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDTO) {
        ModelAndView model = new ModelAndView();
        List<String> errorList = Validators.emptyTest(userDTO);
        if (errorList.size() == 0) {
            List<User> userList = userService.getAllUsers();
            userList.forEach(user -> {
                if (user.getLogin().equals(userDTO.getLogin())) errorList.add("Username already exist");
                if (user.getEmail().equals(userDTO.getEmail())) errorList.add("Email already exist");
            });
            if (errorList.size() == 0) {
                User user = new User();
                user.setLogin(userDTO.getLogin());
                user.setEmail(userDTO.getEmail());
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
                user.setPassword(hashedPassword);
                user.setEnabled(1);
                userService.addUser(user);
                UserRoles userRoles = new UserRoles();
                userRoles.setLogin(userDTO.getLogin());
                userRoles.setRole("ROLE_USER");
                userRoleService.addUserRole(userRoles);
                model.setViewName("index");
                model.addObject("message", "The account was created successfully.");
            } else {
                model.addObject("user", userDTO);
                model.addObject("message", errorList);
                model.setViewName("index");
            }
        } else {
            model.addObject("user", userDTO);
            model.addObject("message", errorList);
            model.setViewName("index");
        }
        return model;
    }


    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("login", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;

    }
}
