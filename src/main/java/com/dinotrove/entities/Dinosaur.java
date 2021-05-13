package com.dinotrove.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "dinosaurs")
public class Dinosaur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DINOSAUR_ID")
    private Long dinosaurId;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(name="DINOSAUR_TYPE")
    private String dinosaurType;
    @Column(name="SIZE_HEIGHT")
    private Double sizeHeight;
    @Column(name="SIZE_WEIGHT")
    private Double sizeWeight;
    @Column(name="SIZE_LENGTH")
    private Double sizeLength;
    private String description;
    @Column(name="ALL_FACTS_DOCUMENT_ID")
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
