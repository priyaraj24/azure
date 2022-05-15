package com.tweetuser.constants;

import org.springframework.stereotype.Component;

@Component
public class ResponseMessage {
	public static final String CREATED = "Record Successfully Created";
	public static final String UPDATED = "Record Updated Successfully";
	public static final String DELETED = "Record Successfully Deleted";
	public static final String FETCHED = "Record Fetched Successfully";
	public static final String MESSAGE_SIZE_ERR = "Tweet should be length of 144";
	public static final String TAG_SIZE_ERR = "Tag should be length of 50";
	public static final String SERVER_ERROR = "Server Error";
	public static final String BAD_REQUEST = "Bad Request";
	public static final String VALIDATED_SUCCESS = "V_SUCCESS";
}
