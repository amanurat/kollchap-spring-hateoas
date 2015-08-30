package kollchap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories
public class KollchapApplication {

  public static void main(String[] args) {
    SpringApplication.run(KollchapApplication.class, args);
  }

  @Bean
  CommandLineRunner init(final GameCharacterRepository gameCharacterRepository) {

    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        gameCharacterRepository.save(new GameCharacter("Slammer Kyntire", "First-level Fighter searching for the Sword of the Sorcerer."));
        gameCharacterRepository.save(new GameCharacter("Hotfa Nap", "First-level Sorceress from a nomad tribe in the Mesta Desert."));
        gameCharacterRepository.save(new GameCharacter("Gripper 'The Skin' Longshank", "First-level Thief from a tribe on the Albine empire border."));
        gameCharacterRepository.save(new GameCharacter("Zhod Thobi", "First-level Cleric joins party as N.P.C and receives equal share of treasure."));
        gameCharacterRepository.save(new GameCharacter("Belisarius", "First-level Thief N.P.C survivor. Currently hiding, if located will join party."));
        gameCharacterRepository.save(new GameCharacter("Rosa Dobbit", "First-level Fighter, N.P.C survivor. Currently captive, if released will join party."));
      }
    };
  }
}
