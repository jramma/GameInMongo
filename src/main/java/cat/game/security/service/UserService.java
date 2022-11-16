package cat.game.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.game.security.domain.Usuario;
import cat.game.security.dto.*;
import cat.game.security.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;

	public Usuario create(CreateUserDto dto) throws Exception {
		if (userRepo.existsByUsername(dto.getUsername())) {
			throw new Exception("El usuario ya existe");
		}
		List<Rol> roles = dto.getRoles().stream().map(rol -> Rol.valueOf(rol)).collect(Collectors.toList());
		Usuario user = new Usuario(dto.getUsername(),dto.getPassword(),roles);
		return userRepo.save(user);

	}

	
}
