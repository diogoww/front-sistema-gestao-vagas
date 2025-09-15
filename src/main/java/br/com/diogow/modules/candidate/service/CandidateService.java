package br.com.diogow.modules.candidate.service;

import br.com.diogow.modules.candidate.dto.Token;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandidateService {

    public Token login(String username, String password){
        RestTemplate rt = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        // Backend exige 'username' no /candidate/auth
        data.put("username", username);
        data.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

        // Fetch raw response first
        ResponseEntity<String> response = rt.exchange(
                "http://localhost:8080/candidate/auth",
                HttpMethod.POST,
                request,
                String.class
        );

        String rawBody = response.getBody();
        System.out.println("[CandidateService.login] Raw response: " + rawBody);

        try {
            // First, try direct mapping to Token (respects @JsonAlias)
            Token token = objectMapper.readValue(rawBody, Token.class);
            if (token == null) {
                token = new Token();
            }
            if (token.getAccess_token() == null || token.getRoles() == null) {
                // Fallback: generic map parsing with several key options and possible nesting under "data"
                Map<String, Object> map = objectMapper.readValue(rawBody, new TypeReference<Map<String, Object>>(){});

                String accessToken = token.getAccess_token();
                if (accessToken == null) {
                    accessToken = extractString(map, "access_token", "accessToken", "token", "acess_token");
                    if (accessToken == null && map.get("data") instanceof Map) {
                        Map<String, Object> dataNode = (Map<String, Object>) map.get("data");
                        accessToken = extractString(dataNode, "access_token", "accessToken", "token", "acess_token");
                    }
                }

                Long expiresIn = token.getExpires_in();
                if (expiresIn == null) {
                    expiresIn = extractLong(map, "expires_in", "expiresIn");
                    if (expiresIn == null && map.get("data") instanceof Map) {
                        Map<String, Object> dataNode = (Map<String, Object>) map.get("data");
                        expiresIn = extractLong(dataNode, "expires_in", "expiresIn");
                    }
                }

                List<String> roles = token.getRoles();
                if (roles == null || roles.isEmpty()) {
                    roles = extractRoles(map);
                    if ((roles == null || roles.isEmpty()) && map.get("data") instanceof Map) {
                        Map<String, Object> dataNode = (Map<String, Object>) map.get("data");
                        roles = extractRoles(dataNode);
                    }
                }

                token.setAccess_token(accessToken);
                token.setExpires_in(expiresIn);
                token.setRoles(roles);
            }

            System.out.println("[CandidateService.login] Parsed token (final): " + token);
            return token;
        } catch (Exception e) {
            System.out.println("[CandidateService.login] Error parsing token: " + e.getMessage());
            // As last resort, try old direct mapping call (may yield nulls)
            var result = rt.postForObject("http://localhost:8080/candidate/auth", request, Token.class);
            System.out.println(result);
            return result;
        }
    }

    private String extractString(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            Object v = map.get(key);
            if (v instanceof String str && !str.isBlank()) {
                return str;
            }
        }
        return null;
    }

    private Long extractLong(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            Object v = map.get(key);
            if (v instanceof Number num) {
                return num.longValue();
            }
            if (v instanceof String str) {
                try {
                    return Long.parseLong(str);
                } catch (NumberFormatException ignored) {}
            }
        }
        return null;
    }

    private List<String> extractRoles(Map<String, Object> map) {
        Object v = map.get("roles");
        if (v instanceof List<?> list) {
            List<String> roles = new ArrayList<>();
            for (Object item : list) {
                if (item instanceof String s && !s.isBlank()) {
                    roles.add(s);
                } else if (item instanceof Map) {
                    // in case roles are objects like {"name":"CANDIDATE"}
                    String name = extractString((Map<String, Object>) item, "name", "role", "authority");
                    if (name != null) {
                        roles.add(name);
                    }
                }
            }
            return roles;
        }
        // sometimes backend returns authorities
        Object a = map.get("authorities");
        if (a instanceof List<?> list) {
            List<String> roles = new ArrayList<>();
            for (Object item : list) {
                if (item instanceof String s && !s.isBlank()) {
                    roles.add(s);
                } else if (item instanceof Map) {
                    String name = extractString((Map<String, Object>) item, "name", "role", "authority");
                    if (name != null) {
                        roles.add(name);
                    }
                }
            }
            return roles;
        }
        return null;
    }
}
