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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/crud/listing")
    public String getCrudListing(@RequestParam(name="dinosaurId", required=false) Long dinosaurId, Model model) {
    	Iterable<Dinosaur> findAll = dinosaurRepository.findAll();
		model.addAttribute("allDinosaurs", findAll);
		Dinosaur editDinosaur = findAll.iterator().next();
		if(dinosaurId != null && dinosaurId == -1) {
			editDinosaur = new Dinosaur();
			editDinosaur.setDinosaurId(Long.valueOf(-1));
		}else if(dinosaurId != null) {
			editDinosaur = dinosaurRepository.findById(dinosaurId).get();
		}
		model.addAttribute("editDinosaur", editDinosaur);
        return "dino_lab";
    }
    @PostMapping("/crud/listing")
    @Transactional
    public String saveCrudListing(@Valid Dinosaur editDinosaur, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dino_lab";
        }
        editDinosaur.setAllFactsDocumentId("1");
        if(editDinosaur.getDinosaurId() == -1) {
        	editDinosaur.setDinosaurId(null);
        }
        dinosaurRepository.save(editDinosaur);
        return "redirect:/dinosaur/crud/listing?dinosaurId="+editDinosaur.getDinosaurId();
    }  
    
    @DeleteMapping("/crud/listing/{dinosaurId}")
    @Transactional
    public ResponseEntity<?> deleteCrudListing(@PathVariable("dinosaurId")Long dinosaurId, Model model) {
        dinosaurRepository.deleteById(dinosaurId);
        return ResponseEntity.ok(null);
    }  
    
   
}
