package cat.game.security.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.game.global.exceptions.AttributeException;
import cat.game.security.domain.Usuario;
import cat.game.security.dto.CreateUserDto;
import cat.game.security.dto.JwtTokenDto;
import cat.game.security.dto.LoginUserDto;
import cat.game.security.dto.Mensaje;
import cat.game.security.service.UserService;

@RestController
@RequestMapping("/auth")
//@CrossOrigin
public class AuthController {
	
	@Autowired
	UserService userService;
	
    @PostMapping("/create")
    public ResponseEntity<Mensaje> create(@Valid @RequestBody CreateUserDto dto) throws AttributeException {
        Usuario usuario = userService.create(dto);
        return ResponseEntity.ok(new Mensaje(HttpStatus.OK, "user " + usuario.getUsername() + " have been created"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@Valid @RequestBody LoginUserDto dto) throws AttributeException {
        JwtTokenDto jwtTokenDto = userService.login(dto);
        return ResponseEntity.ok(jwtTokenDto);
    }
    

}
