package com.dinotrove.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinotrove.entities.DinosaurVideoSummary;
import com.dinotrove.repositories.DinosaurVideoSummaryRepository;

@Controller
@RequestMapping("/reports")
public class ReportsController {

	private Logger logger = LoggerFactory.getLogger(ReportsController.class);

	private final DinosaurVideoSummaryRepository dinosaurVideoSummaryRepository;

	@Autowired
	public ReportsController(DinosaurVideoSummaryRepository dinosaurVideoSummaryRepository) {
		this.dinosaurVideoSummaryRepository = dinosaurVideoSummaryRepository;
	}

	@RequestMapping("/video_summary")
	public String getVideoSummary(Model model) {
		Iterable<DinosaurVideoSummary> allResults = dinosaurVideoSummaryRepository.findAll();
		model.addAttribute("dinosaurVideoSummaries", allResults);
		return "dinosaur_reports";
	}

}
