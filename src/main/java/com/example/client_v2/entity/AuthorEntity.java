package com.example.client_v2.entity;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorEntity {
  private Long id;
  private String lastname;
  private String name;
  private String surname;
  private List<BookEntity> books;

  @Override
  public String toString() {
    return lastname + " " + name + " " + surname + " ";
  }
}
