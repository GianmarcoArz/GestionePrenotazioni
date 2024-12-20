package it.epicode.GestionePrenotazioni.prenotazione;

import it.epicode.GestionePrenotazioni.postazione.Postazione;
import it.epicode.GestionePrenotazioni.utente.Utente;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_prenotazione", nullable = false)
    private LocalDate dataPrenotazione;

    @Column(name = "data_fine_prenotazione", nullable = false)
    private LocalDate dataFinePrenotazione;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "postazione_id", nullable = false)
    private Postazione postazione;


}