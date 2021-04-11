package com.dinotrove.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.dinotrove.entities.Dinosaur;
import com.dinotrove.repositories.DinosaurRepository;

@Controller
@RequestMapping("/dataScraper")
public class DataScraperController {
    
    private final DinosaurRepository dinosaurRepository;

    @Autowired
    public DataScraperController(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }
    
    @GetMapping("/dinosaurs")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<String> getIndex(@RequestParam(name="fetchCount", required=false, defaultValue = "10") Long fetchCount) throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> responseEntity =
				   restTemplate.getForEntity("https://dinosaurpictures.org/api/category/all", Object[].class);
		
		Object[] dinoArray = responseEntity.getBody();
		
		List<Dinosaur> scrapedDinosaurs = new ArrayList<Dinosaur>();
		Dinosaur dinosaur = new Dinosaur();
		int scrapeCount = 0;
		for (Object dino : dinoArray) {
			dinosaur.setName(dino.toString());
			Document dinoDocument = Jsoup.connect("https://dinosaurpictures.org/" + dino + "-pictures").get();
			Elements introDivs = dinoDocument.getElementsByClass("intro");
			Elements descriptionParas = introDivs.get(0).getElementsByTag("p");
			StringBuilder dinoDescription = new StringBuilder();
			Iterator<Element> iterator = descriptionParas.iterator();
			while(iterator.hasNext()) {
				Element para = iterator.next();
				dinoDescription.append(para.text());
				dinoDescription.append("\n");
			}
			dinosaur.setDescription(dinoDescription.toString());
			if(dinoDescription.toString().toLowerCase().contains("carnivore")) {
				dinosaur.setDinosaurType("Carnivore");
			}else if(dinoDescription.toString().toLowerCase().contains("herbivore")) {
				dinosaur.setDinosaurType("Herbivore");
			}else if(dinoDescription.toString().toLowerCase().contains("omnivore")) {
				dinosaur.setDinosaurType("Omnivore");
			}else {
				dinosaur.setDinosaurType("Unknown");
			}
			scrapedDinosaurs.add(dinosaur);
			scrapeCount++;
			if(scrapeCount > fetchCount)
				break;
		}
		
		for(Dinosaur scrapedDinosaur: scrapedDinosaurs) {
			List<Dinosaur> dinosaursWithSameName = dinosaurRepository.findByName(scrapedDinosaur.getName());
			if(CollectionUtils.isEmpty(dinosaursWithSameName)) {
				scrapedDinosaur.setAllFactsDocumentId("1");
				dinosaurRepository.save(scrapedDinosaur);
			}
		}
		
		
    	return new ResponseEntity<>("Scraped successfully",HttpStatus.OK);  
    }

}
