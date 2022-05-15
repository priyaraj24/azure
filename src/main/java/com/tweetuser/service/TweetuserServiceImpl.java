package com.tweetuser.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetuser.dao.TweetDao;
import com.tweetuser.dao.UserDao;
import com.tweetuser.model.Tweet;
import com.tweetuser.model.User;

@Service
public class TweetuserServiceImpl implements TweetuserService {

	@Autowired
	UserDao userDao;

	@Autowired
	TweetDao tweetDao;

	@Autowired

	@Override
	public List<User> getAllUser() {
		return userDao.findAll();
	}

	@Override
	public User getUser(String loginId) {
		return userDao.findByLoginId(loginId);
	}

	@Override
	public Map<String, Object> getAllTweets() {
	/*	List<Tweet> tweets = tweetDao.findAll();
		Map<String, Object> finalRes = new HashMap<>();
		List<Map<String, Object>> resp = new ArrayList<>();
		// parent data
		tweets.stream().filter(f -> f.getReference().isEmpty()).forEach(temp -> {
			Map<String, Object> data = new HashMap<>();
			if (temp.getReference().isEmpty()) {
				temp.setShowDate(this.formatDate(temp.getUpdatedDate()));
				temp.setShowTime(this.formatTime(temp.getUpdatedDate()));
				data.put("data", temp);
				resp.add(data);
			}
		});
		// child-1 data
		for (Map<String, Object> temp : resp) {
			Tweet childPopulate = (Tweet) temp.get("data");
			List<Object> childArray = new ArrayList<>();
			tweets.stream().filter(f -> !f.getReference().isEmpty()).forEach(x -> {
				if (childPopulate.getId().equals(x.getReference())) {
					Map<String, Object> childData = new HashMap<>();
					x.setShowDate(this.formatDate(x.getUpdatedDate()));
					x.setShowTime(this.formatTime(x.getUpdatedDate()));
					childData.put("data", x);
					childArray.add(childData);
				}
			});
			temp.put("children", childArray);
		}
		finalRes.put("data", resp);
		return finalRes;
	}

	@Override
	public Map<String, Object> getTweetByUser(String createdby) {
		List<Tweet> tweetsFinal = new ArrayList<>();
		List<Tweet> tweets = tweetDao.findByCreatedbyAndReference(createdby, "");
		tweetsFinal.addAll(tweets);
		for (Tweet tweet : tweets) {
			List<Tweet> tempChild = tweetDao.findByReference(tweet.getId());
			tweetsFinal.addAll(tempChild);
			for (Tweet tweet2 : tempChild) {
				List<Tweet> tempChild2 = tweetDao.findByReference(tweet2.getId());
				tweetsFinal.addAll(tempChild2);
			}
		}
		Map<String, Object> finalRes = new HashMap<>();
		List<Map<String, Object>> resp = new ArrayList<>();
		// parent data
		tweetsFinal.stream().filter(f -> f.getReference().isEmpty()).forEach(temp -> {
			Map<String, Object> data = new HashMap<>();
			if (temp.getReference().isEmpty()) {
				temp.setShowDate(this.formatDate(temp.getUpdatedDate()));
				temp.setShowTime(this.formatTime(temp.getUpdatedDate()));
				data.put("data", temp);
				resp.add(data);
			}
		});
		// child-1 data
		for (Map<String, Object> temp : resp) {
			Tweet childPopulate = (Tweet) temp.get("data");
			List<Object> childArray = new ArrayList<>();
			tweetsFinal.stream().filter(f -> !f.getReference().isEmpty()).forEach(x -> {
				if (childPopulate.getId().equals(x.getReference())) {
					Map<String, Object> childData = new HashMap<>();
					x.setShowDate(this.formatDate(x.getUpdatedDate()));
					x.setShowTime(this.formatTime(x.getUpdatedDate()));
					childData.put("data", x);
					childArray.add(childData);
				}
			});
			temp.put("children", childArray);
		}
		finalRes.put("data", resp);
		return finalRes; */
		return null;
	}

	@Override
	public void addTweet(Tweet tweet) {
		tweetDao.save(tweet);
	}

	@Override
	public Optional<Tweet> getTweetByIdAndUsername(String id, String username) {
		return tweetDao.findByIdAndCreatedby(id, username);
	}

	@Override
	public Boolean updateTweet(Tweet tweet) {
		Tweet updateObj = new Tweet();
		Optional<Tweet> data = tweetDao.findByIdAndCreatedby(tweet.getId(), tweet.getCreatedby());
		if (data.isPresent()) {
			updateObj = data.get();
			updateObj.setTags(tweet.getTags());
			updateObj.setMessage(tweet.getMessage());
			tweetDao.save(updateObj);
			return true;
		} else
			return false;
	}

	@Override
	public void deleteTweet(String id) {
		List<Tweet> tweets = new ArrayList<>();
		tweets = tweetDao.findByIdOrReference(id, id);
		tweetDao.deleteAll(tweets);
	}

	@Override
	public void replyTweet(Tweet tweet, @Valid String username, @Valid String id) {
		tweet.setCreatedby(username);
		tweet.setReference(id);
		tweetDao.save(tweet);
	}

	@Override
	public void likeTweet(@Valid String username, @Valid String id) {
		Optional<Tweet> tweet;
		Tweet dbData = new Tweet();
		tweet = tweetDao.findById(id);
		List<Map<String, Object>> dbLike = new ArrayList<>();
		if (tweet.isPresent()) {
			dbData = tweet.get();
			Map<String, Object> removeData = null;
			if (dbData.getLikes() != null && dbData.getLikes().size() > 0) {
				dbLike = dbData.getLikes();
				for (Map<String, Object> data : dbLike) {
					if (data.get("username").equals(username)) {
						// flag = (boolean) data.get("like");
						// data.put("like", flag ? false : true);
						removeData = data;
					}
				}
				dbLike.remove(removeData);
			} else {
				Map<String, Object> likeData = new HashMap<>();
				likeData.put("username", username);
				likeData.put("like", true);
				dbLike.add(likeData);
			}

			List<String> likedby = new ArrayList<>();
			if (dbLike != null) {
				for (Map<String, Object> temp : dbLike) {
					if (!likedby.contains((String) temp.get("username")))
						likedby.add((String) temp.get("username"));
				}
			}
			dbData.setLikedBy(likedby);
			dbData.setLikeCount(likedby.size());
			dbData.setLikes(dbLike);
		}
		tweetDao.save(dbData);
	}

	public String formatDate(Date datetime) {
		String format = "dd,MMM yyyy";
		SimpleDateFormat myFormatObj = new SimpleDateFormat(format);
		return myFormatObj.format(datetime);
	}

	public String formatTime(Date datetime) {
		String format = "HH:mm";
		SimpleDateFormat myFormatObj = new SimpleDateFormat(format);
		return myFormatObj.format(datetime);
	}

	@Override
	public Map<String, Object> getTweetByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
