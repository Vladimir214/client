package com.example.client_v2.service;

import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.response.BaseResponse;
import com.example.client_v2.response.DataResponse;
import com.example.client_v2.response.ListResponse;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.Comparator;

public class AuthorService {
  @FXML
  public Button closeButton;
  @Getter
  private ObservableList<AuthorEntity> data = FXCollections.observableArrayList();
  private final HttpService http = new HttpService();
  JsonService service = new JsonService();
  ClientProperties prop = new ClientProperties();
  private Type dataType = new TypeToken<DataResponse<AuthorEntity>>(){
  }.getType();
  private Type listType = new TypeToken<ListResponse<AuthorEntity>>(){
  }.getType();

  public void getAll(){
    ListResponse<AuthorEntity> data = new ListResponse<>();
    data = service.getObject(http.get(prop.getAllAuthor()), listType);
    if(data.isSuccess()) {
      this.data.addAll(data.getData());
      this.data.forEach(System.out::println);
      sort();
    }else{
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Введите данные");
      alert1.show();
    }
  }

  public void add(AuthorEntity data){
    String temp = http.post(prop.getSaveAuthor(), service.getJson(data));
    DataResponse<AuthorEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.add(response.getData());
      sort();
    }else{
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Данные не соответствуют валидации");
      alert1.show();
    }
  }

  public void update(AuthorEntity after, AuthorEntity before){
    System.out.println(before);
    System.out.println(after);
    String temp = http.put(prop.getUpdateAuthor(), service.getJson(after));
    DataResponse<AuthorEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.remove(before);
      this.data.add(after);
      sort();
    } else{
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Данные не соответствуют валидации");
      alert1.show();
    }
  }

  public void delete(AuthorEntity data) {
    String temp = http.delete(prop.getDeleteAuthor(), data.getId());
    BaseResponse response = service.getObject(temp, BaseResponse.class);
    if (response.isSuccess()) {
      this.data.remove(data);
    }else {
      throw new RuntimeException(response.getMessage());
    }
  }

  public void findById(AuthorEntity data){
    String temp = http.get(prop.getFindByIdAuthor() + data.getId());
    DataResponse<AuthorEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.add(response.getData());
    }else{
      throw new RuntimeException(response.getMessage());
    }
  }

  private void sort(){
    data.sort(Comparator.comparing(AuthorEntity::getLastname));
  }
}
