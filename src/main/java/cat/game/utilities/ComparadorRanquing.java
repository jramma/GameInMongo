package cat.game.utilities;

import java.util.Comparator;

import cat.game.security.domain.Usuario;

public class ComparadorRanquing implements Comparator<Usuario>{

	@Override
	public int compare(Usuario o1, Usuario o2) {
		
		return (int) ((int)(o1.getRanquing()*1000) - (o2.getRanquing()*1000));
	}

}
