package com.dinotrove.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    
    @GetMapping("/")
    public void getIndex(HttpServletResponse response) throws IOException {
    	response.sendRedirect("dinosaur/listing");
    }

}
