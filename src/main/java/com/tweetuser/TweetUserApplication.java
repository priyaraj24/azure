package com.tweetuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class TweetUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetUserApplication.class, args);
	}

}
