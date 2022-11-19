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

}
