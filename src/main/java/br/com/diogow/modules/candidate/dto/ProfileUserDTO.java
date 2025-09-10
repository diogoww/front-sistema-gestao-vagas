package br.com.diogow.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUserDTO {

    private String username;
    private UUID id;
    private String name;
    private String email;
    private String description;
}
