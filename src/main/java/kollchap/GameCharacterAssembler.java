package kollchap;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import kollchap.GameCharacterAssembler.GameCharacterResource;

@Component
public class GameCharacterAssembler extends ResourceAssemblerSupport<GameCharacter, GameCharacterResource> {

  public GameCharacterAssembler() {
    super(GameCharacterController.class, GameCharacterResource.class);
  }

  @Override
  public GameCharacterResource toResource(GameCharacter gameCharacter) {
    GameCharacterResource resource = createResourceWithId(gameCharacter.getId(), gameCharacter);
    return resource;
  }

  @Override
  protected GameCharacterResource instantiateResource(GameCharacter entity) {
    return new GameCharacterResource(entity);
  }

  static class GameCharacterResource extends Resource<GameCharacter> {

    public GameCharacterResource(GameCharacter content) {
      super(content);
    }
  }
}