package ru.astronarh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/adminAPI")
public class AdminController {

    @RequestMapping("")
    public String indexController() {
        return "newAdmin";
    }
}
