package com.twit4life.view;

import java.util.List;

import com.twi4life.controller.TwitterAction;

import javafx.concurrent.Task;
import javafx.collections.ObservableList;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TimelineUpdateThread extends Task<Void>  {


	private TwitterAction twitteraction;
	private ObservableList<String> listTweet;
	private List<Status> listStatus;
	private int state =0;
	public TimelineUpdateThread(ObservableList<String> list) {


		this.listTweet = list;


	}

	public TwitterAction getTwitteraction() {
		return twitteraction;
	}
	public void setTwitteraction(TwitterAction twitteraction) {
		this.twitteraction = twitteraction;
	}
	public ObservableList<String> getListTweet() {
		return listTweet;
	}
	public void setListTweet(ObservableList<String> listTweet) {
		this.listTweet = listTweet;
	}
	/*
	 * Methode mettant a jour la liste des tweets toutes les minutes.
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() throws Exception {
		twitteraction = new TwitterAction();


		 while(true) {

			try {
				System.out.println(state++);
				listStatus = twitteraction.getTimeline();
				for(Status s : listStatus){
					listTweet.add(s.getUser().getName() + ":" + s.getText());
				}
				Thread.sleep(60000);// Pause de 1 minute
			} catch (TwitterException e) {
				System.out.println("Twitter exception : ");
				e.printStackTrace();
			}
         catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            break;
        }
		 }

		return null;
	}



}
