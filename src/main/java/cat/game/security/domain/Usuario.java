package cat.game.security.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import cat.game.domain.Partida;
import cat.game.security.dto.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class Usuario {
	@Indexed(unique = true)
	private String username;
	private String password;
	private double ranquing;		
	private String date;
	private ArrayList<Partida> partidas;
	private List<Rol> roles;
	public Usuario(String username, String password, List<Rol> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
}
