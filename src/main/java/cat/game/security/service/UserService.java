package cat.game.security.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cat.game.global.exceptions.AttributeException;
import cat.game.security.domain.Usuario;
import cat.game.security.dto.*;
import cat.game.security.jwt.JwtProvider;
import cat.game.security.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	public JwtTokenDto login(LoginUserDto dto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						dto.getUsername(), dto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		return new JwtTokenDto(token);
	}

	public Usuario create(CreateUserDto dto) throws AttributeException {
		// Comprueba si el usuario ya existe
		if (userRepo.existsByUsername(dto.getUsername())) {
			throw new AttributeException("El usuario ya existe");
		}
		int id = autoIncrement();
		String password = passwordEncoder.encode(dto.getPassword());
		List<Rol> roles = dto.getRoles().stream().map(rol -> Rol.valueOf(rol)).collect(Collectors.toList());

		Usuario user = new Usuario(id, dto.getUsername(), password, roles);

		return userRepo.save(user);

	}

	public int autoIncrement() {
		List<Usuario> users = userRepo.findAll();
		return users.isEmpty() ? 1 : users.stream().max(Comparator.comparing(Usuario::getId)).get().getId() + 1;
	}

}
