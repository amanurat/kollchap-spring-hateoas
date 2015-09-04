package kollchap;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Resources;

public class NestedContentResource<T> {
  private final Resources<T> nested;

  public NestedContentResource(Iterable<T> toNest) {
    this.nested = new Resources<T>(toNest);
  }

  @JsonUnwrapped
  public Resources<T> getNested() {
    return this.nested;
  }

}
