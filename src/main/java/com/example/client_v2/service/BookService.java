package com.example.client_v2.service;

import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.entity.BookEntity;
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

public class BookService {

  @Getter
  private ObservableList<BookEntity> data = FXCollections.observableArrayList();
  private final HttpService http = new HttpService();
  JsonService service = new JsonService();
  ClientProperties prop = new ClientProperties();
  private Type dataType = new TypeToken<DataResponse<BookEntity>>(){
  }.getType();
  private Type listType = new TypeToken<ListResponse<BookEntity>>(){
  }.getType();

  public void getAll(){
    ListResponse<BookEntity> data = new ListResponse<>();
    data = service.getObject(http.get(prop.getAllBook()), listType);
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

  public void add(BookEntity data){
    String temp = http.post(prop.getSaveBook(), service.getJson(data));
    DataResponse<BookEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.add(response.getData());
      sort();
    }else{
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Данные не соответствуют валидации");
      alert1.show();
    }
  }

  public void update(BookEntity after, BookEntity before){
    System.out.println(before);
    System.out.println(after);
    String temp = http.put(prop.getUpdateBook(), service.getJson(after));
    DataResponse<BookEntity> response = service.getObject(temp, dataType);
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

  public void delete(BookEntity data){
    String temp = http.delete(prop.getDeleteBook(), data.getId());
    BaseResponse response = service.getObject(temp, BaseResponse.class);
    if(response.isSuccess()){
      this.data.remove(data);
    }else{
      throw new RuntimeException(response.getMessage());
    }
  }

  public void findById(BookEntity data){
    String temp = http.get(prop.getFindByIdBook() + data.getId());
    DataResponse<BookEntity> response = service.getObject(temp, dataType);
    if(response.isSuccess()){
      this.data.add(response.getData());
    }else{
      throw new RuntimeException(response.getMessage());
    }
  }
  private void sort(){
    data.sort(Comparator.comparing(BookEntity::getTitle));
  }
}
