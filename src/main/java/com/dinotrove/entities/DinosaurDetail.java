package com.dinotrove.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.util.CollectionUtils;

public class DinosaurDetail {

	private Dinosaur dinosaur;

	private String imageLink;

	private List<DinoArticle> dinoArticles;

	public Dinosaur getDinosaur() {
		return dinosaur;
	}

	public void setDinosaur(Dinosaur dinosaur) {
		this.dinosaur = dinosaur;
	}

	public String getImageLink() {
		// TODO Temp fix
		return "/images/" + new Random().nextInt(4) + ".jpg";
		// return imageLink;
	}

	public void setImageLink(String imageLink) {
	}

	public List<DinoArticle> getDinoArticles() {
		if (CollectionUtils.isEmpty(dinoArticles)) {
			dinoArticles = new ArrayList<DinoArticle>();
		}
		return dinoArticles;
	}

	public void setDinoArticles(List<DinoArticle> dinoArticles) {
		this.dinoArticles = dinoArticles;
	}

}
