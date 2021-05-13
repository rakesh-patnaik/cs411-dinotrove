package com.dinotrove.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dinotrove.entities.Video;
import com.dinotrove.entities.VideoDetail;
import com.dinotrove.repositories.VideoRepository;
import com.dinotrove.utils.StringHelper;

@Controller
@RequestMapping("/videos")
public class VideosController {
    
	private Logger logger = LoggerFactory.getLogger(VideosController.class);
	
    private final VideoRepository videoRepository;

    @Autowired
    public VideosController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }
    
    @RequestMapping("/main")
    public String searchVideos(@RequestParam(defaultValue = "") String searchString, Model model) {
    	String escapeSQL = StringHelper.escapeSQL(searchString);
		String wildCardSearch = StringHelper.makeWildCardString(escapeSQL);
    	model.addAttribute("searchString", escapeSQL);
    	List<Video> userSearchResults = videoRepository.findBySearchString(wildCardSearch);
    	if(CollectionUtils.isEmpty(userSearchResults)) {
    		model.addAttribute("userSearchReturnedEmpty", true);
    		userSearchResults = videoRepository.findBySearchString(StringHelper.makeWildCardString(""));
    	}
    	model.addAttribute("selectedVideo", userSearchResults.get(0));
		model.addAttribute("videos", userSearchResults);
        return "videomain";
    }
    
    @GetMapping("/details/{videoId}")
    public ResponseEntity<?> getVideoDetails(@PathVariable("videoId")Long videoId, Model model) {
    	Optional<Video> findById = videoRepository.findById(videoId);
    	VideoDetail videoDetail = new VideoDetail();
    	videoDetail.setVideo(findById.get());
    	
    	return ResponseEntity.ok(videoDetail);
    }

   
}
