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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="dinosaur_id")
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    private String type;

    @Column(name = "size_height")
    private Double sizeHeight;
    @Column(name = "size_weight")
    private Double sizeWeight;
    @Column(name = "size_length")
    private Double sizeLength;

    private String description;

    @Column(name="all_facts_document_id")
    private String allFactsDocumentId;

    public Dinosaur() {}

    public void setId(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
