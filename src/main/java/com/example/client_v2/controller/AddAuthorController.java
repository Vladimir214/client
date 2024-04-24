package com.example.client_v2.controller;

import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.service.AuthorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddAuthorController {
  private final AuthorService service = new AuthorService();
  private boolean addFlag = true;

  @FXML
  public Button closeButton;
  @FXML
  private ListView<AuthorEntity> dataList;

  @FXML
  private TextField textLastName;

  @FXML
  private TextField textName;

  @FXML
  private TextField textSurname;

  @FXML
  void addAction(ActionEvent event) {
    AuthorEntity author = new AuthorEntity();
    author.setLastname(textLastName.getText());
    author.setName(textName.getText());
    author.setSurname(textSurname.getText());
    if(addFlag){
      service.add(author);
      Stage stage = (Stage) closeButton.getScene().getWindow();
      stage.close();
      } else{
      author.setId((getSelectionElement().getId()));
      service.update(author, getSelectionElement());
      Stage stage = (Stage) closeButton.getScene().getWindow();
      stage.close();
    }
    textLastName.clear();
    textName.clear();
    textSurname.clear();
  }

  @FXML
  void onMouseClickDataList(MouseEvent event) {
    if (event.getButton().equals(MouseButton.PRIMARY)){
      if(event.getClickCount() == 2){
        addFlag = false;
        AuthorEntity temp = getSelectionElement();
        textLastName.setText(temp.getLastname());
        textName.setText(temp.getName());
        textSurname.setText(temp.getSurname());
      }
    }
  }

  private AuthorEntity getSelectionElement(){
    AuthorEntity temp = dataList.getSelectionModel().getSelectedItem();
    return temp;
  }

  @FXML
  void cancelAction(ActionEvent event) {
      textLastName.clear();
      textName.clear();
      textSurname.clear();
      closeButton.setText("Добавить");
    }



  @FXML
  void deleteAction(ActionEvent event) {
    AuthorEntity selectedAuthor = dataList.getSelectionModel().getSelectedItem();
    if (selectedAuthor != null) {
      Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
      alert1.setHeaderText("Удалено");
      alert1.show();
      service.delete(selectedAuthor);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Ничего не выбрано");
      alert.setContentText("Выберите автора");
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
      });


  }
}