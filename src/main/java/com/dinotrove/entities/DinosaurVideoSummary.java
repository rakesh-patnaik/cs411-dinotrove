package com.dinotrove.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dinosaur_video_summary")
public class DinosaurVideoSummary {

	@Id
	@Column(name = "DINOSAUR_ID")
	private Long dinosaurId;
	@Column(name = "DINOSAUR_NAME")
	private String name;
	@Column(name = "DINOSAUR_TYPE")
	private String dinosaurType;
	@Column(name = "TOTAL_VIDEO_LENGTH")
	private Double totalVideoLength;
	@Column(name = "MIN_VIDEO_LENGTH")
	private Double minVideoLength;
	@Column(name = "MAX_VIDEO_LENGTH")
	private Double maxVideoLength;

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

	public Double getTotalVideoLength() {
		return totalVideoLength;
	}

	public void setTotalVideoLength(Double totalVideoLength) {
		this.totalVideoLength = totalVideoLength;
	}

	public Double getMinVideoLength() {
		return minVideoLength;
	}

	public void setMinVideoLength(Double minVideoLength) {
		this.minVideoLength = minVideoLength;
	}

	public Double getMaxVideoLength() {
		return maxVideoLength;
	}

	public void setMaxVideoLength(Double maxVideoLength) {
		this.maxVideoLength = maxVideoLength;
	}

}
