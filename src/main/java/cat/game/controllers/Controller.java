package cat.game.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cat.game.domain.Partida;
import cat.game.security.domain.Usuario;

import cat.game.security.service.UserService;
import cat.game.utilities.ComparadorRanquing;

@RestController
public class Controller {

	@Autowired
	UserService userService;

	@PutMapping("/players")
	public Usuario modificarUsuario(@RequestBody String nombre, Authentication auth) {
		Usuario usuario = userService.buscarUsuario(auth.getName()).get();
		usuario.setUsername(nombre);
		return userService.guardarUsuario(usuario);
	}

	@PostMapping("/jugar")
	public Partida jugar(Authentication auth) {
		Partida partida = new Partida(auth.getName());
		userService.guardarPartida(partida);
		Usuario usuario = userService.buscarUsuario(auth.getName()).get();
		usuario.getPartidas().add(partida);
		usuario.setRanquing(caluleteRanquing(usuario.getPartidas()));
		userService.guardarUsuario(usuario);
		return partida;
	}

	@DeleteMapping("/eliminarPartidas")
	public Usuario elimiarJugadas(Authentication auth) {
		Usuario usuario = userService.buscarUsuario(auth.getName()).get();
		ArrayList<Partida> partidas = new ArrayList<>();
		usuario.setPartidas(partidas);
		userService.eliminarPartidas(usuario);		
		return usuario;
	}

	@GetMapping("/getAll")
	public List<Usuario> obtenerTodos() {
		return userService.obtenerTodos();

	}

	@GetMapping("/getJugadas/{id}")
	public List<Partida> obtenerJugadas(@PathVariable int id) {
		Usuario usuario = userService.buscarUsuario(id);
		return usuario.getPartidas();
	}

	@GetMapping("/mediaTotal")
	public double mediaTotal() {
		return caluleteRanquing(userService.obtenerPartidasTotales());
	}

	@GetMapping("/loser")
	public Usuario obtenerLoser() {
		List<Usuario> usuarios = userService.obtenerTodos();
		usuarios.sort(new ComparadorRanquing());

		return usuarios.get(0);

	}

	@GetMapping("/winner")
	public Usuario obtenerWinner() {
		List<Usuario> usuarios = userService.obtenerTodos();
		usuarios.sort(new ComparadorRanquing());

		return usuarios.get(usuarios.size() - 1);

	}

	private double caluleteRanquing(List<Partida> partidas) {
		int victorias = 0;
		int derrotas = 0;
		double media = 0;

		if (partidas != null) {
			for (int j = 0; j < partidas.size(); j++) {
				if (partidas.get(j).getResultado().equalsIgnoreCase("victory")) {
					victorias++;
				} else {
					derrotas++;
				}
			}
			media = (double) victorias / (victorias + derrotas);
		}
		return media;
	}
}
