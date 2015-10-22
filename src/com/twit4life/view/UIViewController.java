package com.twit4life.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.twi4life.dao.TwitterAction;
import com.twit4life.utils.AsyncImageProperty;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import twitter.client.Main;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class UIViewController implements Initializable {

	@FXML
	private ListView<String> listStatus;
	private ObservableList<String> statusData ;
	private Main main ;
	private BorderPane rootLayout;
	@FXML
	private Button updateButton;
	private TwitterAction action;
	@FXML
	private TextField textFieldStatus;
	@FXML
	private ImageView profilPicture;


	public UIViewController() {
		statusData = FXCollections.observableArrayList();
		action = new TwitterAction();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			getProfilImage();
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (TwitterException e) {

			e.printStackTrace();
		}
	}

	  /**
     * Methode lancant le thread de la Timeline
     * afin qu'elle puisse se mettre à jour automatiquement
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

            		statusData.clear();
            		statusData = test.getListTweet();
            		statusData.addListener((ListChangeListener.Change<? extends String> change) -> {

            			 if(change.next()){
            				 System.out.println("ajout");
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

    @FXML
    public void getProfilImage() throws IllegalStateException, TwitterException{
    	Twitter twitter =  TwitterFactory.getSingleton();
    	profilPicture = new ImageView();
    	String  url = twitter.showUser(twitter.getId()).getProfileImageURL();
    	AsyncImageProperty imageProperty = new AsyncImageProperty();
    	profilPicture.imageProperty().bind(imageProperty);
    	imageProperty.imageUrlProperty().set(url);


    }

}







