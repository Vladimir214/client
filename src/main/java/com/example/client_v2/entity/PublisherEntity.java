package com.example.client_v2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherEntity {
  private Long id;
  private String title;
  private CityEntity city;
  private List<BookEntity> books;

  @Override
  public String toString() {
    return title + "(г. " + city + ") ";
  }
}