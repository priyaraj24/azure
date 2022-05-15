package com.tweetuser.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseMessageUtil {

	public ResponseEntity<Object> errorResponse(Exception e, HttpStatus message);

	public ResponseEntity<Object> dataInsertedResponse(String message);

	public ResponseEntity<Object> dataDeletedResponse(String message);

	public ResponseEntity<Object> badRequestResponse(String message);
	
	public ResponseEntity<Object> validateSuccess(String message);
}