package com.dinotrove.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinotrove.entities.DinosaurVideoSummary;
import com.dinotrove.entities.Video;
import com.dinotrove.repositories.DinosaurVideoSummaryRepository;
import com.dinotrove.repositories.VideoRepository;

@Controller
@RequestMapping("/reports")
public class ReportsController {

	private Logger logger = LoggerFactory.getLogger(ReportsController.class);

	private final DinosaurVideoSummaryRepository dinosaurVideoSummaryRepository;
	private final VideoRepository videoRepository;

	@Autowired
	public ReportsController(DinosaurVideoSummaryRepository dinosaurVideoSummaryRepository,
			VideoRepository videoRepository) {
		this.dinosaurVideoSummaryRepository = dinosaurVideoSummaryRepository;
		this.videoRepository = videoRepository;
	}

	@RequestMapping("/video_summary")
	public String getVideoSummary(Model model) {
		Iterable<DinosaurVideoSummary> allResults = dinosaurVideoSummaryRepository.findVideoSummary();
		model.addAttribute("dinosaurVideoSummaries", allResults);
		return "dino_video_summary_reports";
	}

	@RequestMapping("/top100_reports")
	public String getTop100Reports(Model model) {
		Iterable<Video> allResults = videoRepository
				.findVideosThatFeatureAsTop100ForDinoCountOrAsTop100ForVideoLength();
		model.addAttribute("videos", allResults);
		return "dino_top100_reports";
	}
}
