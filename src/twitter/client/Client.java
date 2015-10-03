package twitter.client;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {


	private Stage primaryStage;
    private BorderPane rootLayout;

	 /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() throws IOException {



        // Load person overview.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/ClientVue.fxml"));
		rootLayout = (BorderPane) loader.load();
		AnchorPane personOverview = (AnchorPane) loader.load();

		rootLayout.setCenter(personOverview);


		 Scene scene = new Scene(rootLayout);
         primaryStage.setScene(scene);


            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	@Override
	public void start(Stage primaryStage) throws IOException {
		 this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("AddressApp");

	       // initRootLayout();

	        showPersonOverview();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
