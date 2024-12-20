package it.epicode.GestionePrenotazioni.service;


import com.github.javafaker.Faker;
import it.epicode.GestionePrenotazioni.edificio.Edificio;
import it.epicode.GestionePrenotazioni.edificio.EdificioRepository;
import it.epicode.GestionePrenotazioni.postazione.Postazione;
import it.epicode.GestionePrenotazioni.postazione.PostazioneRepository;
import it.epicode.GestionePrenotazioni.prenotazione.Prenotazione;
import it.epicode.GestionePrenotazioni.prenotazione.PrenotazioniRepository;
import it.epicode.GestionePrenotazioni.utente.Utente;
import it.epicode.GestionePrenotazioni.utente.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class PrenotazioneUtenteService {


    private final PrenotazioniRepository prenotazioniRepository;
    private final PostazioneRepository postazioneRepository;
    private final UtenteRepository utenteRepository;
    private final EdificioRepository edificioRepository;

    public void createPrenotazioneWithScanner(Utente utente, Long edificioId) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            List<Postazione> postazioni = postazioneRepository.findByEdificioId(edificioId);
            System.out.println("Quale postazione desideri ? :");
            for (int i = 0; i < postazioni.size(); i++) {
                System.out.println((i + 1) + ". " + postazioni.get(i).getDescrizione() + " - " + postazioni.get(i).getTipo());
            }
            int postazioneIndex = scanner.nextInt() - 1;
            Postazione postazione = postazioni.get(postazioneIndex);

            while (true) {
                System.out.println("Inserisci la data di prenotazione (yyyy-MM-dd):");
                String dataPrenotazioneStr = scanner.next();
                LocalDate dataPrenotazione = LocalDate.parse(dataPrenotazioneStr);

                List<Prenotazione> prenotazioni = prenotazioniRepository.findByPostazioneAndDataPrenotazione(postazione, dataPrenotazione);
                if (!prenotazioni.isEmpty()) {
                    System.out.println("La postazione è già prenotata per questa data. Vuoi prenotare per un altro giorno? (s/n)");
                    String risposta = scanner.next();
                    if (!risposta.equalsIgnoreCase("s")) {
                        return;
                    }
                    continue;
                }

                List<Prenotazione> prenotazioniUtente = prenotazioniRepository.findByUtenteAndDataPrenotazione(utente, dataPrenotazione);
                if (!prenotazioniUtente.isEmpty()) {
                    System.out.println("L'utente ha già una prenotazione per questa data. Vuoi prenotare per un altro giorno? (s/n)");
                    String risposta = scanner.next();
                    if (!risposta.equalsIgnoreCase("s")) {
                        return;
                    }
                    continue;
                }

                Prenotazione prenotazione = new Prenotazione();
                prenotazione.setUtente(utente);
                prenotazione.setPostazione(postazione);
                prenotazione.setDataPrenotazione(dataPrenotazione);
                prenotazione.setDataFinePrenotazione(dataPrenotazione.plusDays(1));

                prenotazioniRepository.save(prenotazione);
                System.out.println("Prenotazione creata con successo.");

                System.out.println("Vuoi prenotare un'altra postazione in una data differente? (s/n)");
                String risposta = scanner.next();
                if (!risposta.equalsIgnoreCase("s")) {
                    System.out.println("Vuoi terminare l'utilizzo del terminale o prenotare con un altro utente? (terminare/altro)");
                    String scelta = scanner.next();
                    if (scelta.equalsIgnoreCase("terminare")) {
                        System.out.println("Grazie per aver utilizzato il nostro servizio. Arrivederci!");
                        return;
                    } else if (scelta.equalsIgnoreCase("altro")) {
                        selectAndCreatePrenotazioneForAnotherUser();
                        return;
                    }
                }
                break;
            }
        }
    }

    private void selectAndCreatePrenotazioneForAnotherUser() {
        Scanner scanner = new Scanner(System.in);

        List<Utente> utenti = utenteRepository.findAll();
        System.out.println("Seleziona un utente:");
        for (int i = 0; i < utenti.size(); i++) {
            System.out.println((i + 1) + ". " + utenti.get(i).getNomeCompleto());
        }
        int utenteIndex = scanner.nextInt() - 1;
        Utente nuovoUtente = utenti.get(utenteIndex);

        List<Edificio> edifici = edificioRepository.findAll();
        System.out.println("Seleziona un edificio:");
        for (int i = 0; i < edifici.size(); i++) {
            System.out.println((i + 1) + ". " + edifici.get(i).getNome() + " - " + edifici.get(i).getCitta());
        }
        int edificioIndex = scanner.nextInt() - 1;
        Edificio edificio = edifici.get(edificioIndex);

        createPrenotazioneWithScanner(nuovoUtente, edificio.getId());
    }
}
