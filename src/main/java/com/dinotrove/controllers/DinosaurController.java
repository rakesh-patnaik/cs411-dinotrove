package com.dinotrove.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dinotrove.entities.Dinosaur;
import com.dinotrove.entities.DinosaurDetail;
import com.dinotrove.repositories.DinosaurRepository;
import com.dinotrove.utils.StringHelper;

@Controller
@RequestMapping("/dinosaur")
public class DinosaurController {
    
	private Logger logger = LoggerFactory.getLogger(DinosaurController.class);
	
    private final DinosaurRepository dinosaurRepository;

    @Autowired
    public DinosaurController(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }
    
    @RequestMapping("/main")
    public String searchDinosaurs(@RequestParam(defaultValue = "") String searchString, Model model) {
    	String escapeSQL = StringHelper.escapeSQL(searchString);
		String wildCardSearch = StringHelper.makeWildCardString(escapeSQL);
    	model.addAttribute("searchString", escapeSQL);
    	List<Dinosaur> userSearchResults = dinosaurRepository.findBySearchString(wildCardSearch);
    	if(CollectionUtils.isEmpty(userSearchResults)) {
    		model.addAttribute("userSearchReturnedEmpty", true);
    		userSearchResults = dinosaurRepository.findBySearchString(StringHelper.makeWildCardString(""));
    	}
    	model.addAttribute("selectedDinosaur", userSearchResults.get(0));
		model.addAttribute("dinosaurs", userSearchResults);
        return "main";
    }
    
    @GetMapping("/details/{dinosaurId}")
    public ResponseEntity<?> getDinosaurDetails(@PathVariable("dinosaurId")Long dinosaurId, Model model) {
    	Optional<Dinosaur> findById = dinosaurRepository.findById(dinosaurId);
    	DinosaurDetail dinosaurDetail = new DinosaurDetail();
    	dinosaurDetail.setDinosaur(findById.get());
    	//TODO set image details
    	return ResponseEntity.ok(dinosaurDetail);
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
        return "redirect:/main";
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
            dinosaur.setDinosaurId(id);
            return "update-dinosaur";
        }
        
        dinosaurRepository.save(dinosaur);

        return "redirect:/main";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDinosaur(@PathVariable("id") long id, Model model) {
        Dinosaur dinosaur = dinosaurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dinosaur Id:" + id));
        dinosaurRepository.delete(dinosaur);
        
        return "redirect:/main";
    }
}
