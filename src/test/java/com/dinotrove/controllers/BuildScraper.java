package com.dinotrove.controllers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BuildScraper {

	public static void main(String args[]) throws Exception {
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<Object[]> responseEntity =
//				   restTemplate.getForEntity("https://dinosaurpictures.org/api/category/all", Object[].class);
//		
//		Object[] dinoArray = responseEntity.getBody();
		
//		Document dinoDocument = Jsoup.connect("https://dinosaurpictures.org/" + "Gigantoraptor" + "-pictures").get();
//		Elements introDivs = dinoDocument.getElementsByClass("intro");
//		Elements descriptionParas = introDivs.get(0).getElementsByTag("p");
//		StringBuilder dinoDescription = new StringBuilder();
//		while(descriptionParas.iterator().hasNext()) {
//			Element para = descriptionParas.iterator().next();
//			dinoDescription.append(para.text());
//			dinoDescription.append("\n");
//		}
		
		
		
//		System.out.printf("Title: %s\n", dinoDescription.toString());

//            BufferedReader br = new BufferedReader(new InputStreamReader(BuildScraper.class.getResourceAsStream("/dinosaur-data/kaggle-dinosaur.csv")));
//            String newDinoLine = null;
//            while ((newDinoLine = br.readLine()) != null) {
//            	System.out.println(newDinoLine);
//            }	

		

		Document dinoVideoSearchDocument = Jsoup.connect("https://www.youtube.com/results?search_query=judiceratops").get();
		Elements videoTitleElements = dinoVideoSearchDocument.select("#video-title");
		StringBuilder dinoDescription = new StringBuilder();
		while(videoTitleElements.iterator().hasNext()) {
			String videoLink = videoTitleElements.next().attr("href");
			System.out.println(videoLink);
		}		
	}
}
