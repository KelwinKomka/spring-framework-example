package com.servico.controller.api;

import com.servico.config.security.TokenService;
import com.servico.model.dto.TokenDTO;
import com.servico.model.form.LoginForm;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile(value = {"prod", "test"})
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authManager, TokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form) {
        try {
            Authentication authentication = authManager.authenticate(form.getAuth());
            String token = tokenService.createToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
