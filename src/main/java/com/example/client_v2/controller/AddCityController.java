package com.example.client_v2.controller;

import com.example.client_v2.entity.CityEntity;
import com.example.client_v2.service.CityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddCityController {

  private final CityService service = new CityService();
  private boolean addFlag = true;
  @FXML
  public Button closeButton;
  @FXML
  private ListView<CityEntity> dataList;

  @FXML
  private TextField textTitle;

  @FXML
  void addAction(ActionEvent event) {
    CityEntity city = new CityEntity();
    city.setTitle(textTitle.getText());
    if (addFlag) {
      service.add(city);
      Stage stage = (Stage) closeButton.getScene().getWindow();
      stage.close();
    } else {
      city.setId(getSelectionElement().getId());
      service.update(city, getSelectionElement());
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
        CityEntity temp = getSelectionElement();
        textTitle.setText(temp.getTitle());
      }
    }
  }
  private CityEntity getSelectionElement(){
  CityEntity temp = dataList.getSelectionModel().getSelectedItem();
    return temp;
  }
  @FXML
  void cancelAction(ActionEvent event) {
      textTitle.clear();
    closeButton.setText("Добавить");
  }

  @FXML
  void deleteAction(ActionEvent event) {
    CityEntity selectedCity = dataList.getSelectionModel().getSelectedItem();
    if (selectedCity != null) {
      service.delete(selectedCity);
      Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
      alert1.setHeaderText("Удалено");
      alert1.show();
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Ничего не выбрано");
      alert.setContentText("Выберите город");
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

