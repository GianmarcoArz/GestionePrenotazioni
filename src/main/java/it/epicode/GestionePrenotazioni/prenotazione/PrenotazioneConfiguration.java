package it.epicode.GestionePrenotazioni.prenotazione;

import com.github.javafaker.Faker;
import it.epicode.GestionePrenotazioni.postazione.Postazione;
import it.epicode.GestionePrenotazioni.utente.Utente;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

@RequiredArgsConstructor
@Configuration
@Order(5)
public class PrenotazioneConfiguration {

    private final Faker faker;

    @Bean
    @Scope("prototype")
    public Prenotazione prenotazione(Utente utente, Postazione postazione) {
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setPostazione(postazione);
        prenotazione.setDataPrenotazione(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        prenotazione.setDataFinePrenotazione(prenotazione.getDataPrenotazione().plusDays(1));
        return prenotazione;
    }
}