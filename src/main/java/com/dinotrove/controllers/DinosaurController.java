package com.dinotrove.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinotrove.entities.Dinosaur;
import com.dinotrove.repositories.DinosaurRepository;

@Controller
@RequestMapping("/dinosaur")
public class DinosaurController {
    
    private final DinosaurRepository dinosaurRepository;

    @Autowired
    public DinosaurController(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }
    
    @GetMapping("/listing")
    public String showDinosaurList(Model model) {
        model.addAttribute("dinosaurs", dinosaurRepository.findAll());
        return "listing";
    }
    
    @GetMapping("/adddinosaur")
    public String showAddDinosaurForm(Dinosaur dinosaur) {
        return "add-dinosaur";
    }
    
    @PostMapping("/adddinosaur")
    public String addDinosaur(@Valid Dinosaur dinosaur, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-dinosaur";
        }
        
        dinosaurRepository.save(dinosaur);
        return "redirect:/listing";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Dinosaur dinosaur = dinosaurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dinosaur Id:" + id));
        model.addAttribute("dinosaur", dinosaur);
        
        return "update-dinosaur";
    }
    
    @PostMapping("/update/{id}")
    public String updateDinosaur(@PathVariable("id") long id, @Valid Dinosaur dinosaur, BindingResult result, Model model) {
        if (result.hasErrors()) {
            dinosaur.setId(id);
            return "update-dinosaur";
        }
        
        dinosaurRepository.save(dinosaur);

        return "redirect:/listing";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDinosaur(@PathVariable("id") long id, Model model) {
        Dinosaur dinosaur = dinosaurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dinosaur Id:" + id));
        dinosaurRepository.delete(dinosaur);
        
        return "redirect:/listing";
    }
}
