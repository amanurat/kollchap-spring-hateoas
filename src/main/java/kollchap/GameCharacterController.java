package kollchap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/characters")
public class GameCharacterController {

  private final GameCharacterAssembler assembler;

  private final GameCharacterRepository repository;

  @Autowired
  public GameCharacterController(GameCharacterAssembler assembler, GameCharacterRepository repository) {
    this.assembler = assembler;
    this.repository = repository;
  }

  @RequestMapping(method = RequestMethod.GET)
  NestedContentResource<GameCharacterAssembler.GameCharacterResource> all() {
    return new NestedContentResource<>(
        assembler.toResources(repository.findAll()));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  Resource<GameCharacter> gameCharacter(@PathVariable("id") long id) {
    return this.assembler.toResource(repository.findOne(id));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(method = RequestMethod.POST)
  HttpHeaders create(@RequestBody GameCharacterInput characterInput) {
    GameCharacter character = new GameCharacter(characterInput.getName(), characterInput.getBackground());
    character = repository.save(character);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(linkTo(GameCharacterController.class).slash(character.getId()).toUri());
    return httpHeaders;
  }

}