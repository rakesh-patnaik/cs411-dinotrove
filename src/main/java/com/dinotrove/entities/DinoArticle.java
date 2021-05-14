package com.dinotrove.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("DinoArticle")
public class DinoArticle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long articleId;

	private Long dinosaurId;

	private String articleAuthor;

	private Date publishDate;

	private String publisher;

	private List<String> articlePages;

	private String articleSummary;

	private String tableOfContents;

	private List<String> references;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getDinosaurId() {
		return dinosaurId;
	}

	public void setDinosaurId(Long dinosaurId) {
		this.dinosaurId = dinosaurId;
	}

	public String getArticleAuthor() {
		return articleAuthor;
	}

	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public List<String> getArticlePages() {
		return articlePages;
	}

	public void setArticlePages(List<String> articlePages) {
		this.articlePages = articlePages;
	}

	public String getArticleSummary() {
		return articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	public String getTableOfContents() {
		return tableOfContents;
	}

	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
	}

	public List<String> getReferences() {
		return references;
	}

	public void setReferences(List<String> references) {
		this.references = references;
	}

}
