package com.topica.demojspservlet.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo implements Serializable {

  private Long id;

  private String name;

  private String description;
}