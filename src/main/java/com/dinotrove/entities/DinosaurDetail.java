package com.dinotrove.entities;

import java.util.Random;

public class DinosaurDetail {

	private Dinosaur dinosaur;

	private String imageLink;
	
	public Dinosaur getDinosaur() {
		return dinosaur;
	}

	public void setDinosaur(Dinosaur dinosaur) {
		this.dinosaur = dinosaur;
	}

	public String getImageLink() {
		//TODO Temp fix
		return "/images/" + new Random().nextInt(4) + ".jpg";
		//return imageLink;
	}

	public void setImageLink(String imageLink) {
	}
	
	
}
