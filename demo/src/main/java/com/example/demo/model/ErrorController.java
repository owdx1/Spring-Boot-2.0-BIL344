package com.example.demo.model;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/error")
public class ErrorController {


    @GetMapping
    public ModelAndView errorMessage(){

        ModelAndView mav = new ModelAndView("error");

        return mav;

    }






}
