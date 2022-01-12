package com.example.springsecuritywebfluxjwt.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
  private String username;
  private List<String> roles;
  private String currentJwt;
}
