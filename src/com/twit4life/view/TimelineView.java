package com.twit4life.view;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
public class TimelineView extends  Scene  {

	FXMLLoader loader;

	public TimelineView(Parent arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub

		try {
            // Load person overview.
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/twit4life/view/ListViewTimeline.fxml"));
            AnchorPane timeLineOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            BorderPane  rootLayout =((BorderPane) this.getRoot());
            rootLayout.setBottom(timeLineOverview);





        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		this.loader = loader;
	}











}
