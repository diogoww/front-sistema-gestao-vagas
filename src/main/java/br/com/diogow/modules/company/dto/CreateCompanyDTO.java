package br.com.diogow.modules.company.dto;

import lombok.Data;

@Data
public class CreateCompanyDTO {

    private String name;
    private String email;
    private String username;
    private String description;
    private String website;
    private String password;
}
