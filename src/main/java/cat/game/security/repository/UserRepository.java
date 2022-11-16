package cat.game.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.game.security.domain.Usuario;

@Repository
public interface UserRepository extends MongoRepository<Usuario, Integer>{
	boolean existsByUsername(String username);
}
