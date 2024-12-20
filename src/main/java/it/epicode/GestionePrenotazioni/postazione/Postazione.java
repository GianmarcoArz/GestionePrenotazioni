package it.epicode.GestionePrenotazioni.postazione;

import it.epicode.GestionePrenotazioni.edificio.Edificio;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "postazioni")
public class Postazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String descrizione;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPostazione tipo;

    @Column(name = "posti_disponibili", nullable = false)
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;


}