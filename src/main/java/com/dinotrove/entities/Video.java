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
@Table(name = "videos")
public class Video {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VIDEO_ID")
    private Long videoId;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(name="VIDEO_TITLE")
    private String videoTitle;
    @Column(name="VIDEO_LENGTH")
    private Double videoLength;
    @Column(name="VIDEO_URL")
    private String videoUrl;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "VIDEO_DINOSAURS", joinColumns = { @JoinColumn(name = "VIDEO_ID", referencedColumnName = "VIDEO_ID") }, inverseJoinColumns = { @JoinColumn(name = "DINOSAUR_ID", referencedColumnName = "DINOSAUR_ID") })
	private Set<Dinosaur> dinosaurs;

    public Video() {}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public Double getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(Double videoLength) {
		this.videoLength = videoLength;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Set<Dinosaur> getDinosaurs() {
		return dinosaurs;
	}

	public void setDinosaurs(Set<Dinosaur> dinosaurs) {
		this.dinosaurs = dinosaurs;
	}

    
    
 
}
