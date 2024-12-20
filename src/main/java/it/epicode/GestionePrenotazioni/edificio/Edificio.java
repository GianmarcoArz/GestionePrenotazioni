package it.epicode.GestionePrenotazioni.edificio;

import it.epicode.GestionePrenotazioni.postazione.Postazione;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "edifici")
public class Edificio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false)
    private String citta;

    @OneToMany(mappedBy = "edificio")
    private List<Postazione> postazioni = new ArrayList<>();


}