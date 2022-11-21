package cat.game.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.game.domain.Partida;
import cat.game.security.domain.Usuario;
import cat.game.security.service.UserService;

//@RestController
public class Controller {

	@Autowired
	UserService userService;

	@PutMapping("/players")
	public Usuario modificarUsuario(String nombre, Authentication auth) {
		Usuario usuario = userService.buscarUsuario(auth.getName()).get();
		usuario.setUsername(nombre);
		return userService.guardarUsuario(usuario);
	}

	@PostMapping("/jugar")
	public Partida jugar(Authentication auth) {
		Partida partida = new Partida(auth.getName());
		Usuario usuario = userService.buscarUsuario(auth.getName()).get();
		usuario.getPartidas().add(partida);
		return partida;
	}

	@DeleteMapping("/eliminarPartidas")
	public Usuario elimiarJugadas(Authentication auth) {
		Usuario usuario = userService.buscarUsuario(auth.getName()).get();
		ArrayList<Partida> partidas = new ArrayList<>();
		usuario.setPartidas(partidas);
		return usuario;

	}
	
	@GetMapping("/getAll")
	public List<Usuario> obtenerTodos(Authentication auth) {
			return userService.obtenerTodos();
	
	}
	
	
}
