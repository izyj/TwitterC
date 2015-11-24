package com.twit4life.view;

import java.util.List;

import com.twi4life.controller.TwitterAction;
import com.twi4life.controller.UIViewController;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.collections.ObservableList;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TimelineUpdateThread extends Task<Void>  {


	private TwitterAction twitteraction;
	private List<HBox> listTweet;
	private List<Status> listStatus;
	private int state =0;
	public TimelineUpdateThread(List<HBox> list) {


		this.listTweet = list;


	}

	public TwitterAction getTwitteraction() {
		return twitteraction;
	}
	public void setTwitteraction(TwitterAction twitteraction) {
		this.twitteraction = twitteraction;
	}
	public List<HBox> getListTweet() {
		return listTweet;
	}
	public void setListTweet(ObservableList<HBox> listTweet) {
		this.listTweet = listTweet;
	}
	/*
	 * Methode mettant a jour la liste des tweets toutes les minutes.
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() throws Exception {
		twitteraction = new TwitterAction();
		UIViewController controller = new UIViewController();

		 while(true) {

			try {
				System.out.println(state++);
				listStatus = twitteraction.getTimeline();

				for(Status s : listStatus){
					HBox hb1 =new HBox();
					Label screenname = new Label(s.getText());
					Label name = new Label("@"+s.getUser().getName());

					ImageView img = new ImageView();
					Image imge = new Image(s.getUser().getProfileImageURL());
					img.setImage(imge);
					hb1.getChildren().add(img);
					hb1.getChildren().add(screenname);
					hb1.getChildren().add(name);
					listTweet.add(hb1);
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
