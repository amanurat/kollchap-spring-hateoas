package kollchap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  Resource<GameCharacter> note(@PathVariable("id") long id) {
    return this.assembler.toResource((repository.findOne(id)));
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


//  @ControllerAdvice
//  class GameCharacterAdvice {
//    @ResponseBody
//    @ExceptionHandler(GameCharacterNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    VndErrors entityNotFoundExceptionHandler(GameCharacterNotFoundException ex) {
//      return new VndErrors("error", ex.getMessage());
//    }
//  }

  @SuppressWarnings("serial")
  class GameCharacterNotFoundException extends RuntimeException {
    public GameCharacterNotFoundException(String id) {
      super(String.format("could not find game character %s.", id));
    }
  }
}