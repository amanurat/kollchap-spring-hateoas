package kollchap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.hateoas.Identifiable;

import javax.persistence.Entity;

@Entity(name = "character")
public class GameCharacter extends AbstractPersistable<Long> implements Identifiable<Long> {

  private String name;
  private String background;

  protected GameCharacter() {
  }

  public GameCharacter(String name, String background) {
    this.name = name;
    this.background = background;
  }

  @Override
  @JsonIgnore
  public Long getId() {
    return super.getId();
  }


  @Override
  @JsonIgnore
  public boolean isNew() {
    return super.isNew();
  }

  public String getName() {
    return name;
  }

  public String getBackground() {
    return background;
  }

}
