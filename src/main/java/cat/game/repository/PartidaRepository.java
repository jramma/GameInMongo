package cat.game.repository;

import cat.game.domain.Partida;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends MongoRepository<Partida, Integer> {

}
