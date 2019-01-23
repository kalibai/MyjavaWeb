package com.test.bll;

public class Article {
	private String tag;
	private String title;
	private String description;
	private String author;
	private String comments;
	private String jpgs;
	public String getJpgs() {
		return jpgs;
	}

	public void setJpgs(String jpgs) {
		this.jpgs = jpgs;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
 
	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
 
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
 
	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
 
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
 
	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
 
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
 
	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
 
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
 
	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
