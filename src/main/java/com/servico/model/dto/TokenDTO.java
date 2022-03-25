package com.servico.model.dto;

public class TokenDTO {

    private final String token;
    private final String tokenType;

    public TokenDTO(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
