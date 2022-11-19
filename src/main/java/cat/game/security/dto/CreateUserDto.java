package cat.game.security.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.index.Indexed;

import cat.game.domain.Partida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
	@NotBlank(message = "username is mandatory")
	//@Indexed(unique = true)
	private String username;
	@NotBlank(message = "password is mandatory")
	private String password;
	private double ranquing;
	private String date;
	private ArrayList<Partida> partidas;
	@NotEmpty(message = "roles are mandatory")
	private List<String> roles;

	public CreateUserDto(@NotBlank(message = "username is mandatory") String username,
			@NotBlank(message = "password is mandatory") String password,
			@NotEmpty(message = "roles are mandatory") List<String> roles) throws Exception {
//		if(username == null) {
//			throw new Exception("username is mandatory");
//		}
//		if(password == null) {
//			throw new Exception("password is mandatory");
//		}
//		if(roles.isEmpty()) {
//			throw new Exception("roles is mandatory");
//		}
//		
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

}
