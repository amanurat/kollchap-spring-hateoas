package kollchap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "characters", path = "characters")
public interface GameCharacterRepository extends CrudRepository<GameCharacter, Long> {

}
