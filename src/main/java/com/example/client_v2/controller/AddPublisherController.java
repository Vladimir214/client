package com.example.client_v2.controller;

import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.entity.CityEntity;
import com.example.client_v2.entity.PublisherEntity;
import com.example.client_v2.service.CityService;
import com.example.client_v2.service.PublisherService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddPublisherController {
  private final CityService cityService = new CityService();
  private final PublisherService service = new PublisherService();
  private final PublisherService publisherService = new PublisherService();
  private boolean addFlag = true;
  @FXML
  public Button closeButton;

  @FXML
  private ComboBox<CityEntity> comboBox;

  @FXML
  private ListView<PublisherEntity> dataList;

  @FXML
  private TextField textTitle;

  @FXML
  void addAction(ActionEvent event) {
    PublisherEntity publisher = new PublisherEntity();
    publisher.setTitle(textTitle.getText());
    publisher.setCity(comboBox.getSelectionModel().getSelectedItem());
    if(addFlag){
      publisherService.add(publisher);
      Stage stage = (Stage) closeButton.getScene().getWindow();
      stage.close();
    }else {
      publisher.setId(getSelectionElement().getId());
      publisherService.update(publisher, getSelectionElement());
      Stage stage = (Stage) closeButton.getScene().getWindow();
      stage.close();
    }
    textTitle.clear();

  }

  @FXML
  void clickedToList(MouseEvent event) {
    if (event.getButton().equals(MouseButton.PRIMARY)){
      if(event.getClickCount() == 2){
        addFlag = false;
        PublisherEntity temp = getSelectionElement();
        textTitle.setText(temp.getTitle());
        comboBox.getSelectionModel().select(temp.getCity());
      }
    }
  }
  private PublisherEntity getSelectionElement(){
    PublisherEntity temp = dataList.getSelectionModel().getSelectedItem();
    return temp;
  }
  @FXML
  void cancelAction(ActionEvent event) {
    textTitle.clear();
    comboBox.setValue(null);
    closeButton.setText("Добавить");
  }

  @FXML
  void deleteAction(ActionEvent event) {
    PublisherEntity selectedPublisher = dataList.getSelectionModel().getSelectedItem();
    if (selectedPublisher != null) {
      Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
      alert1.setHeaderText("Удалено");
      alert1.show();
      service.delete(selectedPublisher);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Ничего не выбрано");
      alert.setContentText("Выберите издательство");
      alert.showAndWait();
    }
  }

  @FXML
  private void initialize(){
    cityService.getAll();
    publisherService.getAll();
    dataList.setItems(publisherService.getData());
    comboBox.setItems(cityService.getData());
    dataList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedAuthor) -> {
      if (selectedAuthor != null) {
        closeButton.setText("Изменить");
      } else {
        closeButton.setText("Добавить");
      }
    });
  }

}

