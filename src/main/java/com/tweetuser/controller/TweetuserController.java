package com.tweetuser.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetuser.constants.ResponseMessage;
import com.tweetuser.dao.TweetDao;
import com.tweetuser.dao.UserDao;
import com.tweetuser.model.Tweet;
import com.tweetuser.model.User;
import com.tweetuser.service.TweetuserService;
import com.tweetuser.util.ResponseMessageUtil;

import io.swagger.annotations.ApiOperation;

@SuppressWarnings({ "static-access" })
@RestController
@RequestMapping(value = { "/" })
public class TweetuserController {

	private static final Logger log = LoggerFactory.getLogger(TweetuserController.class);

	@Autowired
	public ResponseMessageUtil util;

	@Autowired
	ResponseMessage message;

	@Autowired
	public TweetuserService service;

	@Autowired
	public UserDao userDao;

	@Autowired
	public TweetDao tweetDao;

	@ApiOperation(value = "Ping", notes = "Ping Url")
	@GetMapping(value = "/ping")
	public Object test() {
		Map<String,String> data=new HashMap<>();
		data.put("name","user");
		//return "Welcome!!!";
		return data;
	}

	@ApiOperation(value = "Get All User", notes = "All Users")
	@GetMapping(value = "/users/all")
	public ResponseEntity<Object> getAllUsers() {
		try {
			Map<String, Object> resp = new HashMap<>();
			List<User> users = service.getAllUser();
			resp.put("users", users);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Register a new User", notes = "Add Users")
	//@GetMapping(value = "/user/search/{username}")
	public ResponseEntity<Object> getUsersByName(@Valid @PathVariable String username) {
		try {
			User user = service.getUser(username);
			Map<String, Object> resp = new HashMap<>();
			List<User> users = new ArrayList<>();
			users.add(user);
			resp.put("users", users);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get All Tweets", notes = "Show All Tweets")
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllTweets() {
		try {
			Map<String, Object> tweets = service.getAllTweets();
			return new ResponseEntity<>(tweets, HttpStatus.OK);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Search Tweet by User", notes = "show single user tweets")
	//@GetMapping(value = "/{username}")
	public ResponseEntity<Object> searchTweetsByUser(@Valid @PathVariable String username) {
		try {
			Map<String, Object> tweets = service.getTweetByUser(username);
			log.info("USER TWEET RETRIEVED : " + message.FETCHED);
			return new ResponseEntity<>(tweets, HttpStatus.OK);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Search Tweet by User", notes = "show single user tweets")
	//@GetMapping(value = "/{username}/{id}")
	public ResponseEntity<Object> searchTweetById(@PathVariable String id, @PathVariable String username) {
		try {
			Optional<Tweet> tweet = service.getTweetByIdAndUsername(id, username);
			if (tweet.isPresent()) {
				log.info("USER TWEET RETRIEVED : " + message.FETCHED);
				return new ResponseEntity<>(tweet, HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Add a new Tweet", notes = "Add Tweet for user")
	//@PostMapping(value = "/add")
	public ResponseEntity<Object> addTweet(@RequestBody Tweet tweet) {
		try {
			if (this.validateTweets(tweet).getStatusCodeValue() == 202) {
				service.addTweet(tweet);
				log.info("TWEET POSTED : " + message.CREATED);
				return new ResponseEntity<>(util.dataInsertedResponse(message.CREATED), HttpStatus.CREATED);
			} else
				return new ResponseEntity<>(util.badRequestResponse(message.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Update Tweet", notes = "Update particular Tweet for user")
	//@PutMapping(value = "/update")
	public ResponseEntity<Object> updateTweet(@RequestBody Tweet tweet) {
		try {
			if (this.validateTweets(tweet).getStatusCodeValue() == 202) {
				if (service.updateTweet(tweet)) {
					log.info("TWEET UPDATED : " + message.UPDATED);
					return new ResponseEntity<>(util.dataInsertedResponse(message.UPDATED), HttpStatus.OK);
				} else
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else
				return new ResponseEntity<>(util.badRequestResponse(message.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Delete Tweet", notes = "Delete tweet and its reply- By Id or referenceId")
	//@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Object> deleteTweet(@PathVariable String id) {
		try {
			service.deleteTweet(id);
			log.info("TWEET Deleted : " + message.DELETED);
			return new ResponseEntity<>(util.dataDeletedResponse(message.DELETED), HttpStatus.OK);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Add a reply Tweet", notes = "Add Tweet reply for another user tweeet")
	//@PostMapping(value = "/reply/{username}/{id}")
	public ResponseEntity<Object> replyTweet(@Valid @PathVariable String username, @Valid @PathVariable String id,
			@RequestBody Tweet tweet) {
		try {
			if (this.validateTweets(tweet).getStatusCodeValue() == 202) {
				service.replyTweet(tweet, username, id);
				log.info("REPLY TWEET POSTED : " + message.UPDATED);
				return new ResponseEntity<>(util.dataInsertedResponse(message.CREATED), HttpStatus.CREATED);
			} else
				return new ResponseEntity<>(util.badRequestResponse(message.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@ApiOperation(value = "Add a reply Tweet", notes = "Add Tweet reply for another user tweeet")
	//@GetMapping(value = "/like/{username}/{id}")
	public ResponseEntity<Object> likeTweet(@Valid @PathVariable String username, @Valid @PathVariable String id) {
		try {
			if (userDao.existsByLoginId(username) && tweetDao.existsById(id)) {
				service.likeTweet(username,id);
				log.info("REPLY TWEET POSTED : " + message.CREATED);
				return new ResponseEntity<>(util.dataInsertedResponse(message.CREATED), HttpStatus.CREATED);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(message.SERVER_ERROR + " : " + e);
			return this.util.errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResponseEntity<Object>> validateTweets(Tweet tweet) {
		if (tweet.getMessage().length() > 144)
			return new ResponseEntity<>(util.badRequestResponse(message.MESSAGE_SIZE_ERR), HttpStatus.BAD_REQUEST);
		if (tweet.getTags().length() > 50)
			return new ResponseEntity<>(util.badRequestResponse(message.TAG_SIZE_ERR), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.util.validateSuccess(message.VALIDATED_SUCCESS), HttpStatus.ACCEPTED);

	}
}
