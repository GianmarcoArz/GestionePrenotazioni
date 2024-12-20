package it.epicode.GestionePrenotazioni;

import it.epicode.GestionePrenotazioni.edificio.Edificio;
import it.epicode.GestionePrenotazioni.edificio.EdificioRepository;
import it.epicode.GestionePrenotazioni.postazione.Postazione;
import it.epicode.GestionePrenotazioni.postazione.PostazioneRepository;
import it.epicode.GestionePrenotazioni.postazione.TipoPostazione;
import it.epicode.GestionePrenotazioni.prenotazione.Prenotazione;
import it.epicode.GestionePrenotazioni.prenotazione.PrenotazioniRepository;
import it.epicode.GestionePrenotazioni.service.PrenotazioneUtenteService;
import it.epicode.GestionePrenotazioni.utente.Utente;
import it.epicode.GestionePrenotazioni.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class MainRunner implements CommandLineRunner {

    @Autowired
    private PrenotazioneUtenteService prenotazioneUtenteService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private EdificioRepository edificioRepository;

    @Autowired
    private PostazioneRepository postazioneRepository;

    @Autowired
    private PrenotazioniRepository prenotazioniRepository;

    public static void main(String[] args) {
        SpringApplication.run(GestionePrenotazioniApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostra l'elenco degli utenti
            List<Utente> utenti = utenteRepository.findAll();
            System.out.println("Seleziona un utente:");
            for (int i = 0; i < utenti.size(); i++) {
                System.out.println((i + 1) + ". " + utenti.get(i).getNomeCompleto());
            }
            int utenteIndex = scanner.nextInt() - 1;
            Utente utente = utenti.get(utenteIndex);

            // Mostra l'elenco degli edifici
            List<Edificio> edifici = edificioRepository.findAll();
            System.out.println("Seleziona un edificio:");
            for (int i = 0; i < edifici.size(); i++) {
                System.out.println((i + 1) + ". " + edifici.get(i).getNome() + " - " + edifici.get(i).getCitta());
            }
            int edificioIndex = scanner.nextInt() - 1;
            Edificio edificio = edifici.get(edificioIndex);

            while (true) {
                // Mostra la tabella delle prenotazioni
                List<Prenotazione> prenotazioni = prenotazioniRepository.findAll();
                System.out.println("Prenotazioni presenti nel nostro calendario:");
                System.out.printf("%-15s %-20s %-20s %-20s%n", "Data", "Tipo Postazione", "Utente", "Città");
                for (Prenotazione prenotazione : prenotazioni) {
                    System.out.printf("%-15s %-20s %-20s %-20s%n", prenotazione.getDataPrenotazione(), prenotazione.getPostazione().getTipo(), prenotazione.getUtente().getNomeCompleto(), prenotazione.getPostazione().getEdificio().getCitta());
                }

                // Chiedi all'utente se desidera procedere con una prenotazione o vedere le prenotazioni per tipo e città
                System.out.println("Desidera procedere con una prenotazione o vedere tutte le prenotazioni di un tipoPostazione di una determinata città? (prenotazione/vedere)");
                String scelta = scanner.next();

                if (scelta.equalsIgnoreCase("vedere")) {
                    // Chiedi il tipo di postazione
                    System.out.println("Seleziona il tipo di postazione (PRIVATO/OPENSPACE/SALA_RIUNIONI):");
                    String tipoPostazioneStr = scanner.next();
                    TipoPostazione tipoPostazione = TipoPostazione.valueOf(tipoPostazioneStr.toUpperCase());

                    // Chiedi la città
                    System.out.println("Inserisci la città:");
                    String citta = scanner.next();

                    // Mostra le prenotazioni filtrate per tipo di postazione e città
                    List<Prenotazione> prenotazioniFiltrate = prenotazioniRepository.findAll().stream()
                            .filter(p -> p.getPostazione().getTipo() == tipoPostazione && p.getPostazione().getEdificio().getCitta().equalsIgnoreCase(citta))
                            .toList();

                    System.out.println("Prenotazioni per tipo di postazione e città selezionati:");
                    System.out.printf("%-15s %-20s %-20s %-20s%n", "Data", "Tipo Postazione", "Utente", "Città");
                    for (Prenotazione prenotazione : prenotazioniFiltrate) {
                        System.out.printf("%-15s %-20s %-20s %-20s%n", prenotazione.getDataPrenotazione(), prenotazione.getPostazione().getTipo(), prenotazione.getUtente().getNomeCompleto(), prenotazione.getPostazione().getEdificio().getCitta());
                    }
                } else {
                    // Crea la prenotazione
                    prenotazioneUtenteService.createPrenotazioneWithScanner(utente, edificio.getId());
                }

                // Chiedi se l'utente vuole terminare o continuare
                System.out.println("Vuoi terminare l'utilizzo del terminale o continuare? (terminare/continuare)");
                String risposta = scanner.next();
                if (risposta.equalsIgnoreCase("terminare")) {
                    break;
                }
            }

            // Chiedi se l'utente vuole uscire dal programma o continuare con un altro utente
            System.out.println("Vuoi uscire dal programma o continuare con un altro utente? (uscire/altro)");
            String rispostaFinale = scanner.next();
            if (rispostaFinale.equalsIgnoreCase("uscire")) {
                System.out.println("Grazie per aver utilizzato il nostro servizio. Arrivederci!");
                break;
            }
        }
    }
}
