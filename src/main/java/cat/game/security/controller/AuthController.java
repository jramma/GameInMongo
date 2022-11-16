package cat.game.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.game.security.domain.Usuario;
import cat.game.security.dto.CreateUserDto;
import cat.game.security.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<?> create (@Valid @RequestBody CreateUserDto dto) throws Exception{
			Usuario usuario = userService.create(dto);
		return ResponseEntity.ok("Usuario"+ usuario.getUsername()+" creado");
		
	}
}
