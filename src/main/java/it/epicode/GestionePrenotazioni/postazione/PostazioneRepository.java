package it.epicode.GestionePrenotazioni.postazione;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostazioneRepository extends JpaRepository<Postazione, Long> {
    List<Postazione> findByTipoAndEdificio_Citta(TipoPostazione tipo, String citta);
    List<Postazione> findByEdificioId (Long id);
}
