package com.example.client_v2.controller;

import com.example.client_v2.MainApplication;
import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.entity.BookEntity;
import com.example.client_v2.entity.GenreEntity;
import com.example.client_v2.entity.PublisherEntity;
import com.example.client_v2.service.BookService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;


public class MainController {

  private final BookService service = new BookService();

  @FXML
  private TableView<BookEntity> bookTable;

  @FXML
  private TableColumn<AuthorEntity, String> columnAuthor;

  @FXML
  private TableColumn<GenreEntity, String> columnGenre;

  @FXML
  private TableColumn<BookEntity, String> columnNumber;

  @FXML
  private TableColumn<PublisherEntity, String> columnPublisher;

  @FXML
  private TableColumn<BookEntity, String> columnTitle;

  @FXML
  void addBookAction(ActionEvent event) {
    Optional<BookEntity> book = Optional.empty();
      MainApplication.showBookDialog(book);
    }

  @FXML
  void addOrChangeAuthorAction(ActionEvent event) {
    MainApplication.showDialog("add-author-view.fxml", "Работа с авторами");
  }

  @FXML
  void addOrChangeCityAction(ActionEvent event) { MainApplication.showDialog("city-add-view.fxml", "Работа с городами");}

  @FXML
  void addOrChangeGenreAction(ActionEvent event) { MainApplication.showDialog("genre-add-view.fxml", "Работа с жанрами");}

  @FXML
  void addOrChangePublisherAction(ActionEvent event) { MainApplication.showDialog("publisher-add-view.fxml", "Работа с издательствами");}

  @FXML
  void changeBookAction(ActionEvent event) {
    BookEntity selectedBook = bookTable.getSelectionModel().getSelectedItem();
    if (selectedBook == null) {
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Ничего не выбрано");
      alert1.setHeaderText("Выберите книгу");
      alert1.show();
    }else{
    Optional<BookEntity> book = Optional.of(bookTable.getSelectionModel().getSelectedItem());
      MainApplication.showBookDialog(book);
    }
  }



  @FXML
  void closeAction(ActionEvent event) {
  }

  @FXML
  void deleteBookAction(ActionEvent event) {
    BookEntity selectedBook = bookTable.getSelectionModel().getSelectedItem();
    if (selectedBook != null) {
      Alert alert1 = new Alert(Alert.AlertType.WARNING);
      alert1.setHeaderText("Удалено");
      alert1.show();
      service.delete(selectedBook);
    } else {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText("Ничего не выбрано");
      alert.setContentText("Выберите книгу");
      alert.showAndWait();
    }
  }
  @FXML
  private void initialize(){
    service.getAll();
    columnAuthor.setCellValueFactory(new PropertyValueFactory<AuthorEntity, String>("author"));
    columnGenre.setCellValueFactory(new PropertyValueFactory<GenreEntity, String>("genre"));
    columnNumber.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("year"));
    columnTitle.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("title"));
    columnPublisher.setCellValueFactory(new PropertyValueFactory<PublisherEntity, String>("publisher"));
    bookTable.setItems(service.getData());
  }

  private Optional<BookEntity> book = Optional.empty();

  public void setBook(Optional<BookEntity> book){
    this.book = book;
    if(book.isPresent()){
      if(book.get().getId() != null)
        service.update(book.get(),bookTable.getSelectionModel().getSelectedItem());
   else service.add(book.get());
    }

  }
}