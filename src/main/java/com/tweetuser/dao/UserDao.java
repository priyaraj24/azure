package com.tweetuser.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetuser.model.User;

public interface UserDao extends MongoRepository<User, String> {

	public User findByLoginId(String loginId);

	public Boolean existsByLoginId(String loginId);

}
