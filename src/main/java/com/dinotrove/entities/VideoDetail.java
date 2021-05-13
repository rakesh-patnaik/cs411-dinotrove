package com.dinotrove.entities;

public class VideoDetail {

	private Video video;

	private String videoEmbedUrl;
	
	
	public String getVideoEmbedUrl() {
		String videoUrl = video.getVideoUrl();
		String videoHash = videoUrl.split("=")[1];
		return "https://www.youtube.com/embed/" + videoHash;
	}

	public void setVideoEmbedUrl(String videoEmbedUrl) {
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	
	
}
