package com.servico.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostViewController {

    @GetMapping
    public String post() {
        return "posts";
    }

    @ExceptionHandler(IllegalAccessException.class)
    public String onError() {
        return "redirect:/posts";
    }
}
