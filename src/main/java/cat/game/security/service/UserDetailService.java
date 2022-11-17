package cat.game.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cat.game.security.domain.Usuario;
import cat.game.security.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(username).get();
		if(usuario != null)
			return null;
		
		return UsuarioPrincipal.build(usuario);
	}

}
