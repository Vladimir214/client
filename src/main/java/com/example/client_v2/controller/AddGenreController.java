package com.example.client_v2.controller;

import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.entity.CityEntity;
import com.example.client_v2.entity.GenreEntity;
import com.example.client_v2.service.GenreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddGenreController {
  private final GenreService service = new GenreService();
  private boolean addFlag = true;
  @FXML
  public Button closeButton;

  @FXML
  private ListView<GenreEntity> dataList;

  @FXML
  private TextField textTitle;

  @FXML
  void addAction(ActionEvent event) {

  GenreEntity genre = new GenreEntity();
  genre.setTitle(textTitle.getText());
  if (addFlag) {
    service.add(genre);
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
  }else {
    genre.setId(getSelectionElement().getId());
    service.update(genre, getSelectionElement());
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
  }
  textTitle.clear();
  }

  @FXML
  void onMouseClickDataList(MouseEvent event) {
    if (event.getButton().equals(MouseButton.PRIMARY)){
      if(event.getClickCount() == 2){
        addFlag = false;
        GenreEntity temp = getSelectionElement();
        textTitle.setText(temp.getTitle());
      }
    }
  }

  private GenreEntity getSelectionElement(){
    GenreEntity temp = dataList.getSelectionModel().getSelectedItem();
    return temp;
  }
  @FXML
  void cancelAction(ActionEvent event) {
    textTitle.clear();
    closeButton.setText("Добавить");
  }

  @FXML
  void deleteAction(ActionEvent event) {
   GenreEntity selectedGenre = dataList.getSelectionModel().getSelectedItem();
    if (selectedGenre != null) {
      Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
      alert1.setHeaderText("Удалено");
      alert1.show();
      service.delete(selectedGenre);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Ничего не выбрано");
      alert.setContentText("Выберите жанр");
      alert.showAndWait();
    }
  }

  @FXML
  private void initialize(){
    service.getAll();
    dataList.setItems(service.getData());
    dataList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedAuthor) -> {
      if (selectedAuthor != null) {
        closeButton.setText("Изменить");
      } else {
        closeButton.setText("Добавить");
      }
    });;
  }

}
