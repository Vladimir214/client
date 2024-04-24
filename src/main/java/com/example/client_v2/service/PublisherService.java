package com.example.client_v2.service;

import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.entity.PublisherEntity;
import com.example.client_v2.response.BaseResponse;
import com.example.client_v2.response.DataResponse;
import com.example.client_v2.response.ListResponse;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.Comparator;

public class PublisherService {
  @Getter
  private ObservableList<PublisherEntity> data = FXCollections.observableArrayList();
  private final HttpService http = new HttpService();
  JsonService service = new JsonService();
  ClientProperties prop = new ClientProperties();
  private Type dataType = new TypeToken<DataResponse<PublisherEntity>>(){
  }.getType();
  private Type listType = new TypeToken<ListResponse<PublisherEntity>>(){
  }.getType();

  public void getAll(){
    ListResponse<PublisherEntity> data = new ListResponse<>();
    data = service.getObject(http.get(prop.getAllPublisher()), listType);
    if(data.isSuccess()) {
      this.data.addAll(data.getData());
      this.data.forEach(System.out::println);
      sort();
    }else{
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Данные не соответствуют валидации");
      alert1.show();
    }
  }

  public void add(PublisherEntity data){
    String temp = http.post(prop.getSavePublisher(), service.getJson(data));

    DataResponse<PublisherEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.add(response.getData());
      sort();
    }else{
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Данные не соответствуют валидации");
      alert1.show();
    }
  }

  public void update(PublisherEntity data, PublisherEntity selectionElement){
    String temp = http.put(prop.getUpdatePublisher(), service.getJson(data));
    DataResponse<PublisherEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.remove(data);
      this.data.add(response.getData());
    } else{
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Данные не соответствуют валидации");
      alert1.show();
    }
  }

  public void delete(PublisherEntity data){
    String temp = http.delete(prop.getDeletePublisher(), data.getId());
    BaseResponse response = service.getObject(temp, BaseResponse.class);
    if(response.isSuccess()){
      this.data.remove(data);
      sort();
    }else{
      throw new RuntimeException(response.getMessage());
    }
  }

  public void findById(PublisherEntity data){
    String temp = http.get(prop.getFindByIdPublisher() + data.getId());
    DataResponse<PublisherEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.add(response.getData());
    }else{
      throw new RuntimeException(response.getMessage());
    }
  }
  private void sort(){
    data.sort(Comparator.comparing(PublisherEntity::getTitle));
  }
}
