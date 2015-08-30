package kollchap;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameCharacterInput {

  @NotBlank
  private final String name;
  @NotBlank
  private final String background;

  @JsonCreator
  public GameCharacterInput(@JsonProperty("name") String name,
                            @JsonProperty("background") String background) {
    this.name = name;
    this.background = background;
  }

  public String getName() {
    return name;
  }

  public String getBackground() {
    return background;
  }

}