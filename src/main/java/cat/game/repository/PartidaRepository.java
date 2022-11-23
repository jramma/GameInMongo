package cat.game.repository;

import cat.game.domain.Partida;


import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends MongoRepository<Partida, Integer> {
	void deleteByUsuario(String username);
	/*
	 * Esto borra todas las partidas que en propiedad 'usuario' tengan 
	 * el mismo String que paso por el service, 
	 * que llamo en el controller
	 */
}
