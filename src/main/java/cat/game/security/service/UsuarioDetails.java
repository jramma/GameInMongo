package cat.game.security.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cat.game.security.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDetails implements UserDetails {
	private String username;
	private String password;
	private Collection <?extends GrantedAuthority>authorities;
	
	// no construye, devuelve un details de un usuario
	public static UsuarioDetails build(Usuario usuario) {
		Collection<GrantedAuthority> authorities=
				usuario.getRoles().stream().map(rol ->new SimpleGrantedAuthority(rol.name()))
				.collect(Collectors.toList());
		return new UsuarioDetails(usuario.getUsername(),usuario.getPassword(), authorities);
	
	}
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}