package it.epicode.GestionePrenotazioni.service;

import com.github.javafaker.Faker;
import it.epicode.GestionePrenotazioni.edificio.Edificio;
import it.epicode.GestionePrenotazioni.postazione.Postazione;
import it.epicode.GestionePrenotazioni.postazione.PostazioneRepository;
import it.epicode.GestionePrenotazioni.postazione.TipoPostazione;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostazioneEdificioService {

    private final PostazioneRepository postazioneRepository;
    private final Faker faker;

    public void createPostazioniForEdificio(Edificio edificio) {
        createAndSavePostazione(edificio, TipoPostazione.PRIVATO, faker.number().numberBetween(1, 10));
        createAndSavePostazione(edificio, TipoPostazione.OPENSPACE, faker.number().numberBetween(100, 1000));
        createAndSavePostazione(edificio, TipoPostazione.SALA_RIUNIONI, faker.number().numberBetween(5, 25));
    }

    private void createAndSavePostazione(Edificio edificio, TipoPostazione tipo, int postiDisponibili) {
        Postazione postazione = new Postazione();
        postazione.setDescrizione(faker.lorem().sentence());
        postazione.setTipo(tipo);
        postazione.setPostiDisponibili(postiDisponibili);
        postazione.setEdificio(edificio);
        postazioneRepository.save(postazione);
    }
}