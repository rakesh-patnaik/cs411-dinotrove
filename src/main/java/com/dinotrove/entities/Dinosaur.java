package com.dinotrove.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "dinosaurs")
public class Dinosaur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dinosaurId;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String dinosaurType;
    private Double sizeHeight;
    private Double sizeWeight;
    private Double sizeLength;
    private String description;
    private String allFactsDocumentId;

    public Dinosaur() {}

	public Long getDinosaurId() {
		return dinosaurId;
	}

	public void setDinosaurId(Long dinosaurId) {
		this.dinosaurId = dinosaurId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDinosaurType() {
		return dinosaurType;
	}

	public void setDinosaurType(String dinosaurType) {
		this.dinosaurType = dinosaurType;
	}

	public Double getSizeHeight() {
		return sizeHeight;
	}

	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}

	public Double getSizeWeight() {
		return sizeWeight;
	}

	public void setSizeWeight(Double sizeWeight) {
		this.sizeWeight = sizeWeight;
	}

	public Double getSizeLength() {
		return sizeLength;
	}

	public void setSizeLength(Double sizeLength) {
		this.sizeLength = sizeLength;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAllFactsDocumentId() {
		return allFactsDocumentId;
	}

	public void setAllFactsDocumentId(String allFactsDocumentId) {
		this.allFactsDocumentId = allFactsDocumentId;
	}

    
 
}
