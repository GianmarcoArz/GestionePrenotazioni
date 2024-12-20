package it.epicode.GestionePrenotazioni.utente;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(nullable = false)
    private String email;

    






}