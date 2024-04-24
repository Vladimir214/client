package com.example.client_v2;

import com.example.client_v2.controller.AddBookController;
import com.example.client_v2.controller.MainController;
import com.example.client_v2.entity.BookEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainApplication extends Application {
  private FXMLLoader fxmlLoader;
  private static MainController mainController;


  @Override
  public void start(Stage stage) throws IOException {
    fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    stage.setTitle("Библиотека");
    stage.setScene(scene);
    stage.getIcons().add(new Image("icon2.png"));
    mainController = fxmlLoader.getController();
    stage.show();
  }


  public static void main(String[] args) {
    launch();
  }


  public static void showBookDialog(Optional<BookEntity> book) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApplication.class.getResource("add-book-view.fxml"));

      AnchorPane page = (AnchorPane) loader.load();
      Stage dialogStage = new Stage();
      dialogStage.getIcons().add(new Image("icon1.png"));
      dialogStage.setTitle("Работа с книгами");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      AddBookController controller = loader.getController();
      controller.setBook(book);
      controller.start();
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);
      dialogStage.showAndWait();
      book = controller.getBook();
      System.out.println(book);
      mainController.setBook(book);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void showDialog(String nameView, String title){
    try {

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApplication.class.getResource(nameView));
      AnchorPane page = (AnchorPane) loader.load();
      Stage dialogStage = new Stage();
      dialogStage.getIcons().add(new Image("icon1.png"));
      dialogStage.setTitle(title);
      dialogStage.initModality(Modality.WINDOW_MODAL);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);
      dialogStage.show();
    } catch (IOException e){
      e.printStackTrace();
    }
  }


}

