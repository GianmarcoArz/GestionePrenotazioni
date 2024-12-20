package it.epicode.GestionePrenotazioni.postazione;

import com.github.javafaker.Faker;
import it.epicode.GestionePrenotazioni.edificio.Edificio;
import it.epicode.GestionePrenotazioni.edificio.EdificioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@RequiredArgsConstructor
@Configuration
@Order(3)
public class PostazioneConfiguration {

    private final Faker faker;


    @Bean(name = "PRIVATO")
    public Postazione postazionePrivato() {
        Postazione postazione = new Postazione();
        postazione.setDescrizione(faker.lorem().sentence());
        postazione.setTipo(TipoPostazione.PRIVATO);
        postazione.setPostiDisponibili(faker.number().numberBetween(1, 10));

        return postazione;
    }

    @Bean(name = "OPENSPACE")
    public Postazione postazioneOpenSpace() {
        Postazione postazione = new Postazione();
        postazione.setDescrizione(faker.lorem().sentence());
        postazione.setTipo(TipoPostazione.OPENSPACE);
        postazione.setPostiDisponibili(faker.number().numberBetween(100, 1000));
        return postazione;
    }

    @Bean(name = "SALA_RIUNIONI")
    public Postazione postazioneSalaRiunione() {
        Postazione postazione = new Postazione();
        postazione.setDescrizione(faker.lorem().sentence());
        postazione.setTipo(TipoPostazione.SALA_RIUNIONI);
        postazione.setPostiDisponibili(faker.number().numberBetween(5, 25));
        return postazione;
    }
}