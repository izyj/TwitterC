package com.twi4life.controller;

import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;

public interface TwitterMethodsInterface {


	public String postTweet(String message);

	public String connexionTweeter();

	public String updateStatut(String message) throws TwitterException;

	public List<Status> getTimeline() throws TwitterException;

	//public

}
