package com.group9.publishsubscribe.CommonLayer.Serialization;

import java.io.Reader;

public interface ISerializer {
	
	public <T> T deserialize(String json, Class<T> classType);
	
	public <T> T deserialize(Reader stream, Class<T> classType);
	
	public <T> String serialize(T obj);

}