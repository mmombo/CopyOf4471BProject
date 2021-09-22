package com.group9.publishsubscribe.CommonLayer.Serialization;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;

public class SerializerJSONTest {
	
	private final SerializerGson serializer = new SerializerGson();
	private String serializedSubscriber1 = "{\"username\":\"Andrew\",\"password\":\"Bloch-Hansen\",\"subscriptionList\":[\"PriceOverTime\",\"StockInfo\"],\"subscriberIP\":\"127.0.0.1\",\"subscriberPort\":2500}";
	private String serializedSubscriber2 = "{\"username\":\"Eric\",\"password\":\"Chen\",\"subscriptionList\":[\"PriceOverTime\",\"StockInfo\"],\"subscriberIP\":\"127.0.0.1\",\"subscriberPort\":2500}";
	private String serializedSubscriberList = "[{\"username\":\"Andrew\",\"password\":\"Bloch-Hansen\",\"subscriptionList\":[\"PriceOverTime\",\"StockInfo\"],\"subscriberIP\":\"127.0.0.1\",\"subscriberPort\":2500},{\"username\":\"Eric\",\"password\":\"Chen\",\"subscriptionList\":[\"PriceOverTime\",\"StockInfo\"],\"subscriberIP\":\"127.0.0.1\",\"subscriberPort\":2500}]";
	private String[] s = {"PriceOverTime", "StockInfo"};
	private LinkedList<String> subscriptions = new LinkedList<>(Arrays.asList(s));
	private Subscriber deserializedSubscriber1 = new Subscriber("Andrew", "Bloch-Hansen", subscriptions, "127.0.0.1", 2500);
	private Subscriber deserializedSubscriber2 = new Subscriber("Eric", "Chen", subscriptions, "127.0.0.1", 2500);
	private Subscriber[] deserializedSubscriberList = {deserializedSubscriber1, deserializedSubscriber2};
	
	private final double errorMargin = 0.01;
	
	@Test
	public void deserializeStringReturnsCorrectObjectSingle() throws Exception {
		
		Subscriber subscriber = serializer.deserialize(serializedSubscriber1, deserializedSubscriber1.getClass());
		
		assertEquals(deserializedSubscriber1.getUsername(), subscriber.getUsername());
		assertEquals(deserializedSubscriber1.getPassword(), subscriber.getPassword());
		assertEquals(deserializedSubscriber1.getSubscriptionList(), subscriber.getSubscriptionList());	
		
	}
	
	@Test
	public void deserializeStringReturnsCorrectObjectMultiple() throws Exception {
		
		Subscriber[] subscribers = serializer.deserialize(serializedSubscriberList, deserializedSubscriberList.getClass());
		
		assertEquals(deserializedSubscriber1.getUsername(), subscribers[0].getUsername());
		assertEquals(deserializedSubscriber1.getPassword(), subscribers[0].getPassword());
		assertEquals(deserializedSubscriber1.getSubscriptionList(), subscribers[0].getSubscriptionList());	
		
		assertEquals(deserializedSubscriber2.getUsername(), subscribers[1].getUsername());
		assertEquals(deserializedSubscriber2.getPassword(), subscribers[1].getPassword());
		assertEquals(deserializedSubscriber2.getSubscriptionList(), subscribers[1].getSubscriptionList());	

	}
	
	@Test
	public void serializeObjectReturnsCorrectStringSingle() throws Exception {
		
		String subscriber = serializer.serialize(deserializedSubscriber1);
		
		assertEquals(serializedSubscriber1, subscriber);
		
	}
	
	@Test
	public void serializeObjectReturnsCorrectStringMultiple() throws Exception {
		
		String subscribers = serializer.serialize(deserializedSubscriberList);
		
		assertEquals(serializedSubscriberList, subscribers);
		
	}	

}