package br.com.diogow.modules.candidate.dto;

import lombok.Data;

@Data
public class CreateCandidateDTO {

    private String name;
    private String email;
    private String username;
    private String password;
    private String description;
}
