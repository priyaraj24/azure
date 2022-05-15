package com.tweetuser.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.tweetuser.model.Tweet;
import com.tweetuser.model.User;

public interface TweetuserService {

	public List<User> getAllUser();

	public User getUser(String username);

	public Map<String, Object> getAllTweets();

	public Map<String, Object> getTweetByUser(String username);

	public void addTweet(Tweet tweet);

	public Optional<Tweet> getTweetByIdAndUsername(String id, String username);

	public Boolean updateTweet(Tweet tweet);

	public void deleteTweet(String id);

	public void replyTweet(Tweet tweet, @Valid String username, @Valid String id);

	public void likeTweet(@Valid String username, @Valid String id);

}
