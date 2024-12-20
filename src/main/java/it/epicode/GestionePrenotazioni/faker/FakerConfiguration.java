package it.epicode.GestionePrenotazioni.faker;

import com.github.javafaker.Faker;
import it.epicode.GestionePrenotazioni.edificio.Edificio;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration

public class FakerConfiguration {

    @Bean
    public Faker faker() {
        return new Faker();
    }

}
