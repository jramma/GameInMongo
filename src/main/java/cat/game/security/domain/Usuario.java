package cat.game.security.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import cat.game.domain.Partida;
import cat.game.security.dto.Rol;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class Usuario {
	@Id
	private int id;
	private String username;
	private String password;
	private double ranquing;
	private String date;
	private ArrayList<Partida> partidas;
	private List<Rol> roles;

	public Usuario(int id, String username, String password, List<Rol> roles) {
		DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy '@' HH:mm:ss");
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.partidas = new ArrayList<>();
		this.date = dateFormat.format(Calendar.getInstance().getTime());
		this.ranquing = 0;
	}

	public Usuario(int id, String username, String password, double ranquing, String date, ArrayList<Partida> partidas,
			List<Rol> roles) {
		DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy '@' HH:mm:ss");
		this.id = id;
		this.username = username;
		this.password = password;
		this.ranquing = 0;
		this.date = dateFormat.format(Calendar.getInstance().getTime());
		this.partidas = new ArrayList<>();
		this.roles = roles;
	}

	public Usuario() {
		DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy '@' HH:mm:ss");
		this.date = dateFormat.format(Calendar.getInstance().getTime());
		this.partidas = new ArrayList<>();
	}

}
