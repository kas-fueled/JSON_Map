package com.fueled.volleyadditions;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum SingletonObjectMapper {
	INSTANCE;		
	private static ObjectMapper mapperDefault = new ObjectMapper();
		
	public ObjectMapper getDefaultMapper() {
		return mapperDefault;
	}						
}