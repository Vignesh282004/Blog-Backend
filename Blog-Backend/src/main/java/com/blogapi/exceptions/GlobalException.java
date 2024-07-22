package com.blogapi.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapi.Daos.ApiResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handelMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		HashMap<String, String> map = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach(

				(error) -> {
					String fieldName = ((FieldError) (error)).getField();
					String message = error.getDefaultMessage();
					map.put(fieldName, message);
				});
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AppException.class)
	public ResponseEntity<ApiResponse> handleAppException(AppException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, false), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ResourceException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, false), HttpStatus.NOT_FOUND);
	}

}
