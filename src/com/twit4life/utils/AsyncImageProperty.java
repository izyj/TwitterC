package com.twit4life.utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.scene.image.Image;

public class AsyncImageProperty extends SimpleObjectProperty<Image> {
  private final ImageLoadService imageLoadService = new ImageLoadService();
  private final ObjectProperty<String> imageUrl = new SimpleObjectProperty<>();

  public AsyncImageProperty() {
    imageLoadService.stateProperty().addListener(new ChangeListener<State>() {
      @Override
      public void changed(ObservableValue<? extends State> observable, State oldValue, State value) {
        if(value == State.SUCCEEDED) {
          set(imageLoadService.getValue());
        }
        if(value == State.FAILED) {
          set(null);
        }
        if(value == State.SUCCEEDED || value == State.CANCELLED || value == State.FAILED) {
          String handle = imageUrl.get();
          if(handle != null && !handle.equals(imageLoadService.imageUrl)) {
            loadImageInBackground(handle);
          }
        }
      }
    });

    imageUrl.addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String value) {
        if(!imageLoadService.isRunning()) {
          loadImageInBackground(imageUrl.getValue());
        }
      }
    });
  }

  public ObjectProperty<String> imageUrlProperty() {
    return imageUrl;
  }

  private void loadImageInBackground(String imageUrl) {
    synchronized(imageLoadService) {
      if(imageUrl != null) {
        imageLoadService.setImageUrl(imageUrl);
        imageLoadService.restart();
      }
    }
  }

  private static class ImageLoadService extends Service<Image> {
    private String imageUrl;

    public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
    }

    @Override
    protected Task<Image> createTask() {
      final String imageUrl = this.imageUrl;

      return new Task<Image>() {
        @Override
        protected Image call() {
          return new Image(imageUrl);
        }
      };
    }
  }
}