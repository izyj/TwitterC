package com.twi4life.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import twitter4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.twit4life.modele.Twit4lifeKeys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TwitterAction implements TwitterMethodsInterface {

	private static Logger LOG = Logger.getLogger(TwitterAction.class);
	private Twitter twitter;
	public String postTweet(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	public String connexionTweeter() {

		String result = "SUCCESS";
		try {
		    // The factory instance is re-useable and thread safe.
			twitter = TwitterFactory.getSingleton();
		    twitter.setOAuthConsumer(Twit4lifeKeys.consumerKeys.getKey(), Twit4lifeKeys.consumerKeys.getSecret());
		    RequestToken requestToken = twitter.getOAuthRequestToken();
		    AccessToken accessToken = new AccessToken(Twit4lifeKeys.accessTokenKeys.getKey(), Twit4lifeKeys.accessTokenKeys.getSecret());
		    twitter.setOAuthAccessToken(accessToken);
		    LOG.info("L'application s'est connecter correctement.");

		}
	   catch (TwitterException te) {
	        if(401 == te.getStatusCode()){
	        	result = "ERROR";
	        }else{
	          te.printStackTrace();
	        }
	      }


		return result;
	}

	public String updateStatut(String message) throws TwitterException {
		twitter = TwitterFactory.getSingleton();
		Status status = twitter.updateStatus(message);
		LOG.info("Statut successfully updated.");
		return null;



	}

	/**
	 * r�cupere la liste des status de twitter
	 * @return liste status
	 */
	public List<Status> getTimeline() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
	    List<Status> statuses = twitter.getHomeTimeline();
	    LOG.info("Timeline sent.");

	    return statuses;
	}





}
