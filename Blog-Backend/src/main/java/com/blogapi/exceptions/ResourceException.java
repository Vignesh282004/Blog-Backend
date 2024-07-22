package com.blogapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceException  extends RuntimeException{
	//customer exception for service exceptions
	String resorceName;
	String fieldName;
	long id;
	public ResourceException(String resorceName, String fieldName, long id) {
		super(String.format("%s is not found with %s : %s",fieldName, resorceName,id));
		this.resorceName = resorceName;
		this.fieldName = fieldName;
		this.id = id;
	}
	
	
	
}
