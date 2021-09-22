package com.group9.publishsubscribe.CommonLayer.Serialization;

import java.io.Reader;

import com.google.gson.Gson;

public class SerializerGson implements ISerializer {
	
	private final Gson gson;
	
	public SerializerGson() {
		
		gson = new Gson();
		
	}
	
	@Override
	public <T> T deserialize(String json, Class<T> classType) {
		
		return gson.fromJson(json, classType);
		
	}
	
	@Override
	public <T> T deserialize(Reader stream, Class<T> classType) {
		
		return gson.fromJson(stream, classType);
		
	}
	
	@Override
	public <T> String serialize(T obj) {
		
		return gson.toJson(obj);
		
	}
	
}