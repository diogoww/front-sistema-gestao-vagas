package br.com.diogow.modules.candidate.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

	@JsonAlias({"accessToken", "token", "acess_token"})
	private String access_token;
	private List<String> roles;
	@JsonAlias({"expiresIn"})
	private Long expires_in;
}
