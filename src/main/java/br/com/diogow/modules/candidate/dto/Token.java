package br.com.diogow.modules.candidate.dto;

import lombok.Data;

import java.util.List;

@Data
public class Token {

    private String access_token;
    private List<String> roles;
    private Long expires_in;
}
