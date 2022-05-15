package com.tweetuser.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

@Document("tweet")
public class Tweet {

	@Id
	private String id;

	@Field("login_id")
	private String createdby;

	@Size(min = 3, message = "{validation.name.size.too_short}")
	@Size(max = 144, message = "{validation.name.size.too_long}")
	private String message;

	@Size(min = 0, message = "{validation.name.size.too_short}")
	@Size(max = 50, message = "{validation.name.size.too_long}")
	private String tags;

	private String reference;

	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date createdDate;

	@LastModifiedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date updatedDate;

	private List<Map<String, Object>> likes=new ArrayList<>();

	private List<Map<String, Object>> disLikes;

	private String showDate;

	private String showTime;

	private int likeCount;

	private List<String> likedBy;

	public List<String> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(List<String> likedBy) {
		this.likedBy = likedBy;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public List<Map<String, Object>> getDisLikes() {
		return disLikes;
	}

	public void setDisLikes(List<Map<String, Object>> disLikes) {
		this.disLikes = disLikes;
	}

	public List<Map<String, Object>> getLikes() {
		return likes;
	}

	public void setLikes(List<Map<String, Object>> likes) {
		this.likes = likes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", createdby=" + createdby + ", message=" + message + ", tags=" + tags
				+ ", reference=" + reference + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

	public Tweet(String id, String createdby, String message, String tags, String reference, Date createdDate,
			Date updatedDate) {
		super();
		this.id = id;
		this.createdby = createdby;
		this.message = message;
		this.tags = tags;
		this.reference = reference;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public Tweet() {
		super();
	}

}
