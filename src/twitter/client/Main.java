package twitter.client;

import java.io.IOException;

import com.twi4life.controller.TwitterAction;
import com.twi4life.controller.UIViewController;
import com.twit4life.view.TimelineView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twitter4j.TwitterException;

public class Main extends Application {


	private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<String> statusData = FXCollections.observableArrayList();
    private Button updateButton;
    private TwitterAction action;

    public Main() {
    		action = new TwitterAction();
    		action.connexionTweeter();


	}
	 /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {

            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/twit4life/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     * @throws TwitterException
     */
    public void showIhmOverview() throws TwitterException {
    	// Show the scene containing the root layout.
        TimelineView scene = new TimelineView(rootLayout);
        UIViewController controller = scene.getLoader().getController();
        //updateButton.setOnAction(controller.updateStatus(getUpdateButton()));
        primaryStage.setScene(scene);
        controller.updateTimeline(this);
        controller.getProfilImage();




    }




    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	@Override
	public void start(Stage primaryStage) throws IOException, TwitterException {
		 this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("TwitterC");

	        initRootLayout();

	        showIhmOverview();


	        primaryStage.setResizable(false);
	        primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<String> getStatusData() {
        return statusData;
    }

    public Button getUpdateButton() {
		return updateButton;
	}

    public void setUpdateButton(Button updateButton) {
		this.updateButton = updateButton;
	}

}
