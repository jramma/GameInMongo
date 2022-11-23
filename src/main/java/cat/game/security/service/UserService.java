package cat.game.security.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cat.game.domain.Partida;
import cat.game.global.exceptions.AttributeException;
import cat.game.repository.PartidaRepository;
import cat.game.security.domain.Usuario;
import cat.game.security.dto.*;
import cat.game.security.jwt.JwtProvider;
import cat.game.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PartidaRepository partidaRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	AuthenticationManager authenticationManager;
	
	

	public Usuario create(CreateUserDto dto) throws AttributeException {
		if (userRepo.existsByUsername(dto.getUsername()))
			throw new AttributeException("username already in use");

		return userRepo.save(mapUserFromDto(dto));
	}

	public JwtTokenDto login(LoginUserDto dto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		return new JwtTokenDto(token);
	}

	private Usuario mapUserFromDto(CreateUserDto dto) {
		int id = autoIncrement(userRepo.findAll());
		String password = passwordEncoder.encode(dto.getPassword());
		List<Rol> roles = dto.getRoles().stream().map(rol -> Rol.valueOf(rol)).collect(Collectors.toList());
		return new Usuario(id, dto.getUsername(), password, roles);
	}


	public int autoIncrement(List<Usuario> list) {
		int id;
		if (list.isEmpty()) {
			id = 1;
		} else {
			id = list.stream().max(Comparator.comparing(Usuario::getId)).get().getId() + 1;
		}
		return id;
	}
	public Optional<Usuario> buscarUsuario(String nombre) {
		return userRepo.findByUsername(nombre);
	}
	public Usuario guardarUsuario(Usuario usuario) {
		return userRepo.save(usuario);
	}
	public List<Usuario> obtenerTodos(){
		return userRepo.findAll();
	}
	public List<Partida> obtenerPartidasTotales(){
		return partidaRepository.findAll();
	}
	public Partida guardarPartida(Partida partida){
		return partidaRepository.save(partida);
	}
	public Usuario buscarUsuario(int id) {
		return userRepo.findById(id).get();
		
	}

	public void eliminarPartidas(Usuario usuario) {
		String username = usuario.getUsername();
		partidaRepository.deleteByUsuario(username);
		userRepo.save(usuario);
	}
}
