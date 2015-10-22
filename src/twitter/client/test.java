package twitter.client;

import com.twi4life.controller.TwitterAction;
import com.twit4life.utils.AsyncImageProperty;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class test extends Application {

	 @Override
	    public void start(Stage primaryStage) throws IllegalStateException, TwitterException {

		 	TwitterAction action = new TwitterAction();
		 	action.connexionTweeter();
		 	Twitter twitter =  TwitterFactory.getSingleton();
		 	ImageView profilPicture = new ImageView();
	    	String  url = twitter.showUser(twitter.getId()).getOriginalProfileImageURL();
	    	AsyncImageProperty imageProperty = new AsyncImageProperty();
	    	profilPicture.imageProperty().bind(imageProperty);
	    	imageProperty.imageUrlProperty().set(url);


	    	profilPicture.setFitWidth(100);
	    	profilPicture.setPreserveRatio(true);
	    	profilPicture.setSmooth(true);
	    	profilPicture.setCache(true);

	        StackPane root = new StackPane();


	        //root.getChildren().add(profilPicture);
	        root.getChildren().add(profilPicture);
	        Scene scene = new Scene(root, 300, 250);

	        primaryStage.setTitle("Hello World!");
	        primaryStage.setScene(scene);
	        primaryStage.show();




	    }
	 public static void main(String[] args) {
	        launch(args);
	    }
}
