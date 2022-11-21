package cat.game.security.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
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
	@Id
	private int id;
	// @Indexed(unique = true)
	private String username;
	private String password;
	private double ranquing;
	private String date;
	private ArrayList<Partida> partidas;
	private List<Rol> roles;

	public Usuario(int id, String username, String password, List<Rol> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.partidas = new ArrayList<>();
		this.ranquing = 0;
	}

	public double caluleteRanquing() {
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
