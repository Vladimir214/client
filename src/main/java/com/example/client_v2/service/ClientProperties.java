package com.example.client_v2.service;

import com.example.client_v2.MainApplication;
import lombok.Getter;

import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Stream;

@Getter
public class ClientProperties {
  private final Properties properties = new Properties();
  private String allBook;
  private String findByIdBook;
  private String saveBook;
  private String updateBook;
  private String findByAuthorInBook;
  private String findByTitleInBook;
  private String allAuthor;
  private String findByIdAuthor;
  private String saveAuthor;
  private String updateAuthor;
  private String allCity;
  private String findByIdCity;
  private String saveCity;
  private String updateCity;
  private String allGenre;
  private String findByIdGenre;
  private String saveGenre;
  private String updateGenre;
  private String allPublisher;
  private String findByIdPublisher;
  private String savePublisher;
  private String updatePublisher;
  private String deleteAuthor;
  private String deleteBook;
  private String deleteCity;
  private String deleteGenre;
  private String deletePublisher;

  public ClientProperties() {
    try(

        InputStream input = MainApplication.class.getClassLoader().getResourceAsStream("config.properties")) {
      System.out.println(input);
      properties.load(input);
      allBook = properties.getProperty("book.getAll");
      findByIdBook = properties.getProperty("book.findById");
      saveBook = properties.getProperty("book.save");
      updateBook = properties.getProperty("book.update");
      findByAuthorInBook = properties.getProperty("book.findByAuthor");
      findByTitleInBook = properties.getProperty("book.findByTitle");
      allAuthor = properties.getProperty("author.getAll");
      findByIdAuthor = properties.getProperty("author.findById");
      saveAuthor = properties.getProperty("author.save");
      updateAuthor = properties.getProperty("author.update");
      allCity = properties.getProperty("city.getAll");
      findByIdCity = properties.getProperty("city.findById");
      saveCity = properties.getProperty("city.save");
      updateCity = properties.getProperty("city.update");
      allGenre = properties.getProperty("genre.getAll");
      findByIdGenre = properties.getProperty("genre.findById");
      saveGenre = properties.getProperty("genre.save");
      updateGenre = properties.getProperty("genre.update");
      allPublisher = properties.getProperty("publisher.getAll");
      findByIdPublisher = properties.getProperty("publisher.findById");
      savePublisher = properties.getProperty("publisher.save");
      updatePublisher = properties.getProperty("publisher.update");
      deleteAuthor = properties.getProperty("author.delete");
      deleteBook = properties.getProperty("book.delete");
      deleteCity = properties.getProperty("city.delete");
      deleteGenre = properties.getProperty("genre.delete");
      deletePublisher = properties.getProperty("publisher.delete");

    }catch (
        IOException e){
      e.printStackTrace();
    }
  }
}
