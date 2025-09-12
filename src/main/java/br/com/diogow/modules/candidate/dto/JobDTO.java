package br.com.diogow.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private UUID id;
    private UUID companyId;
    private String description;
    private String benefits;
    private String level;
    private Date creaedAt;
}
