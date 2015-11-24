package com.twi4life.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.twit4life.utils.AsyncImageProperty;
import com.twit4life.view.TimelineUpdateThread;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import twitter.client.Main;
import twitter4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class UIViewController implements Initializable {

	@FXML
	private ListView<HBox> listStatus;
	private ObservableList<HBox> statusData ;
	private Main main ;
	@FXML
	private BorderPane rootLayout;
	@FXML
	private Button updateButton;
	@FXML
	private Button updateTimelineButton;

	private TwitterAction action;
	@FXML
	private TextField textFieldStatus;
	@FXML
	private ImageView profilPicture  = new ImageView();
	@FXML
	private Label follow = new Label();

	@FXML
	private Label followers = new Label();

	private Twitter twitter;
	int compt = 0;
	private static Logger LOG = Logger.getLogger(TwitterAction.class);


	public UIViewController() {
		statusData = FXCollections.observableArrayList();
		action = new TwitterAction();

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		twitter =  TwitterFactory.getSingleton();
		getProfilImage();
		getNumberOfFollowerAnd();

	}

	/**
	 * Methode lancant le thread de la Timeline
	 * afin qu'elle puisse se mettre à jour automatiquement
	 * (refresh toute les minutes
	 * @param main
	 * @return
	 * @return
	 * @throws TwitterException
	 */
	public  void updateTimeline(Main main) throws TwitterException {
		this.main = main;

		//String status;

		TimelineUpdateThread test = new TimelineUpdateThread(statusData);

		listStatus.setItems(statusData);
		Thread thd = new Thread(test);
		Timer timer = new Timer();

		timer.scheduleAtFixedRate (new TimerTask() {
			public void run()
			{
				Platform.runLater(new Runnable() {
					public void run() {
						getNumberOfFollowerAnd();
						statusData.clear();
						statusData.addAll(test.getListTweet());
						statusData.addListener((ListChangeListener<? super HBox>)(ListChangeListener.Change<? extends Object> change) -> {
							if(change.next()){
								LOG.info("Timeline have been updated.");
							}
						});

					}

				});
			}

		}, 0, 60000);
		thd.start();


	}


	/**
	 * Methode mettant ajoutant un nouveau tweet a la timeline
	 * @throws TwitterException
	 */
	@FXML
	public void updateStatus() throws TwitterException{


		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				try {
					action.updateStatut(textFieldStatus.getText());

				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	/**
	 * Methode qui récupère et affiche l'image de profil
	 */
	public void getProfilImage() {

		AsyncImageProperty imageProperty;
		try {
			imageProperty = getImageFromId(twitter.getId());
			profilPicture.imageProperty().bind(imageProperty);
		} catch (IllegalStateException | TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * Methode qui récupère l'image correspondand a un ID
	 * @param id
	 * @return
	 */
	public AsyncImageProperty getImageFromId(long id){
		AsyncImageProperty imageProperty = null;
		try {

			String url;
			url = twitter.showUser(id).getBiggerProfileImageURL();
			imageProperty = new AsyncImageProperty();
			imageProperty.imageUrlProperty().set(url);

		} catch (IllegalStateException | TwitterException e) {
			e.printStackTrace();
		}

		return imageProperty;
	}

	public void getNumberOfFollowerAnd(){
		try {
			followers.setText(Integer.toString(twitter.showUser(twitter.getId()).getFollowersCount()));
			follow.setText(Integer.toString(twitter.showUser(twitter.getId()).getFriendsCount()));
		} catch (IllegalStateException | TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}
	public BorderPane getRootLayout() {
		return rootLayout;
	}
	/**
	 * Methode de mise à jour de la timeline
	 * @author Jonathan
	 */
	@FXML
	public void updateTimeline(){

		updateTimelineButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				try {
					List<Status> listStatus;
					listStatus = action.getTimeline();
					statusData.clear();
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
						statusData.add(hb1);

						Platform.runLater(new Runnable() {
							public void run() {
								statusData.addListener((ListChangeListener<? super HBox>)(ListChangeListener.Change<? extends Object> change) -> {
									if(change.next()){
										LOG.info("Timeline have been updated.");
									}
								});

							}

						});
					}
				} catch (TwitterException p) {

					p.printStackTrace();
				}

			}
		});






	}




}







