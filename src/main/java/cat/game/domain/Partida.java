package cat.game.domain;

import java.io.Serializable;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Partida implements Serializable {

	private static final long serialVersionUID = 1L;
	private String usuario;
	private int dado1;
	private int dado2;
	private String resultado;

	public Partida(String usuario) {
	
		String resultado1;
		this.dado1 = (int) (Math.random() * 6 + 1);
		this.dado2 = (int) (Math.random() * 6 + 1);
		if (dado1 + dado2 == 7) {
			resultado1 = "victory";
		} else {
			resultado1 = "you lose";
		}
		this.resultado = resultado1;
		this.usuario = usuario;
	}

}
