package com.group9.publishsubscribe.CommonLayer.Serialization;

import java.io.IOException;
import java.io.Reader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializerJackson implements ISerializer {

	
	/**
	 * If you want to use the Jackson library, you will need a variable of type ObjectMapper.
	 */
	private final ObjectMapper mapper;

	public SerializerJackson() {

		mapper = new ObjectMapper();

	}

	/**
	 * This method will transform a String into an object, but you need to tell it what class it turns into.
	 * It is okay to give it the parent class and let it automatically determine the subclass.
	 * @param json the string in json format
	 * @param classType the class (or parent class) that you expect the String to represent
	 * @return an object of type classType
	 */
	@Override
	public <T> T deserialize(String json, Class<T> classType) {

		try {
			return mapper.readValue(json, classType);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	/**
	 * This method will transform a stream into an object, but you need to tell it what class it turns into.
	 * It is okay to give it the parent class and let it automatically determine the subclass.
	 * @param stream the input stream
	 * @param classType the class (or parent class) that you expect the String to represent
	 * @return an object of type classType
	 */
	@Override
	public <T> T deserialize(Reader stream, Class<T> classType) {

		try {
			return mapper.readValue(stream, classType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	/**
	 * This method will transform an object into a String in json format. You do not need to specify anything about
	 * the object type.
	 * @param the object
	 * @return the String in json format
	 */
	@Override
	public <T> String serialize(T obj) {

		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;

	}

}