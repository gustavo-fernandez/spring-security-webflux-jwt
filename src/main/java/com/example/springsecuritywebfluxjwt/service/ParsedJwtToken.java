package com.example.springsecuritywebfluxjwt.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParsedJwtToken {

  private String username;
  private List<String> roles;

}
