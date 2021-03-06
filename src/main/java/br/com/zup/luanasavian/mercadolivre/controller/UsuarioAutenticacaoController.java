package br.com.zup.luanasavian.mercadolivre.controller;

import br.com.zup.luanasavian.mercadolivre.request.TokenDto;
import br.com.zup.luanasavian.mercadolivre.config.security.TokenServiceClass;
import br.com.zup.luanasavian.mercadolivre.request.LoginFormRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("usuarios/login")
public class UsuarioAutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenServiceClass tokenServiceClass;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginFormRequest form) {
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenServiceClass.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer")); //Bearer é um dos mecanismos de autenticação utilizados no protocolo HTTP
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
