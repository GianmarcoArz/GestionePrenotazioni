package it.epicode.GestionePrenotazioni.prenotazione;

import it.epicode.GestionePrenotazioni.postazione.Postazione;
import it.epicode.GestionePrenotazioni.utente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioniRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByPostazioneAndDataPrenotazione(Postazione postazione, LocalDate dataPrenotazione);
    List<Prenotazione> findByUtenteAndDataPrenotazione(Utente utente, LocalDate dataPrenotazione);

}
