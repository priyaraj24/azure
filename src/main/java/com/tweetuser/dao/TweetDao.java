package com.tweetuser.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetuser.model.Tweet;

public interface TweetDao extends MongoRepository<Tweet, String> {

	public List<Tweet> findByCreatedbyAndReference(String createdby, String reference);

	public Optional<Tweet> findByIdAndCreatedby(String id, String username);

	public List<Tweet> findByIdOrReference(String id, String reference);

	public List<Tweet> findByReference(String reference);
}
