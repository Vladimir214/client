package com.example.client_v2.controller;

import com.example.client_v2.entity.AuthorEntity;
import com.example.client_v2.entity.BookEntity;
import com.example.client_v2.entity.GenreEntity;
import com.example.client_v2.entity.PublisherEntity;
import com.example.client_v2.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;


public class AddBookController {

  private final BookService service = new BookService();
  @Setter
  @Getter
  private Optional<BookEntity> book;

  @FXML
  public Button closeButton;

  private final AuthorService authorService = new AuthorService();
  private final GenreService genreService = new GenreService();
  private final PublisherService publisherService = new PublisherService();

  @FXML
  private ComboBox<AuthorEntity> comboBoxAuthor;

  @FXML
  private ComboBox<GenreEntity> comboBoxGenre;

  @FXML
  private ComboBox<PublisherEntity> comboBoxPublisher;

  @FXML
  private TextField textTitle;

  @FXML
  private TextField textYear;

  @FXML
  void addAction(ActionEvent event) {
    BookEntity temp = BookEntity.builder()
        .title(textTitle.getText())
        .year(textYear.getText())
        .genre(comboBoxGenre.getSelectionModel().getSelectedItem())
        .publisher(comboBoxPublisher.getSelectionModel().getSelectedItem())
        .author(comboBoxAuthor.getSelectionModel().getSelectedItem())
        .build();
    if(book.isEmpty()){
      book = Optional.of(temp);
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }else{
      temp.setId(book.get().getId());
      book = Optional.of(temp);
      Stage stage = (Stage) closeButton.getScene().getWindow();
      stage.close();
    }


  }

  @FXML
  void cancelAction(ActionEvent event) {
      textTitle.clear();
      textYear.clear();
      comboBoxAuthor.setValue(null);
      comboBoxPublisher.setValue(null);
      comboBoxGenre.setValue(null);
    closeButton.setText("Добавить");
  }


  public void start(){
    if(book.isPresent()){
      textTitle.setText(book.get().getTitle());
      textYear.setText(book.get().getYear());
      comboBoxAuthor.setValue(book.get().getAuthor());
      comboBoxGenre.setValue(book.get().getGenre());
      comboBoxPublisher.setValue(book.get().getPublisher());
    }
  }

  @FXML
  private void initialize(){
    authorService.getAll();
    publisherService.getAll();
    genreService.getAll();
    comboBoxAuthor.setItems(authorService.getData());
    comboBoxGenre.setItems(genreService.getData());
    comboBoxPublisher.setItems(publisherService.getData());

  }

}