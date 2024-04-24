package com.example.client_v2.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {
  private Long id;
  private String title;
  private AuthorEntity author;
  private PublisherEntity publisher;
  private GenreEntity genre;
  private String year;

  @Override
  public String toString() {
    return  title + " " + author + publisher + genre + year + " ";
  }
}
