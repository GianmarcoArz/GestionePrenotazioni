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
            Utente utente = selectUtente(scanner);
            Edificio edificio = selectEdificio(scanner);

            while (true) {
                mostraPrenotazioni();

                System.out.println("Desidera procedere con una prenotazione o vedere tutte le prenotazioni di un tipoPostazione di una determinata città? (prenotazione/vedere)");
                String scelta = scanner.next();

                if (scelta.equalsIgnoreCase("vedere")) {
                    mostraPrenotazioniFiltrate(scanner);
                } else {
                    prenotazioneUtenteService.createPrenotazioneWithScanner(utente, edificio.getId());
                }

                System.out.println("Vuoi terminare l'utilizzo del terminale o continuare? (terminare/continuare)");
                if (scanner.next().equalsIgnoreCase("terminare")) {
                    break;
                }
            }

            System.out.println("Vuoi uscire dal programma o continuare con un altro utente? (uscire/altro)");
            if (scanner.next().equalsIgnoreCase("uscire")) {
                System.out.println("Grazie per aver utilizzato il nostro servizio. Arrivederci!");
                break;
            }
        }
    }

    private Utente selectUtente(Scanner scanner) {
        List<Utente> utenti = utenteRepository.findAll();
        System.out.println("Seleziona un utente:");
        for (int i = 0; i < utenti.size(); i++) {
            System.out.println((i + 1) + ". " + utenti.get(i).getNomeCompleto());
        }
        return utenti.get(scanner.nextInt() - 1);
    }

    private Edificio selectEdificio(Scanner scanner) {
        List<Edificio> edifici = edificioRepository.findAll();
        System.out.println("Seleziona un edificio:");
        for (int i = 0; i < edifici.size(); i++) {
            System.out.println((i + 1) + ". " + edifici.get(i).getNome() + " - " + edifici.get(i).getCitta());
        }
        return edifici.get(scanner.nextInt() - 1);
    }

    private void mostraPrenotazioni() {
        List<Prenotazione> prenotazioni = prenotazioniRepository.findAll();
        System.out.println("Prenotazioni presenti nel nostro calendario:");
        System.out.printf("%-15s %-20s %-20s %-20s%n", "Data", "Tipo Postazione", "Utente", "Città");
        for (Prenotazione prenotazione : prenotazioni) {
            System.out.printf("%-15s %-20s %-20s %-20s%n", prenotazione.getDataPrenotazione(), prenotazione.getPostazione().getTipo(), prenotazione.getUtente().getNomeCompleto(), prenotazione.getPostazione().getEdificio().getCitta());
        }
    }

    private void mostraPrenotazioniFiltrate(Scanner scanner) {
        System.out.println("Seleziona il tipo di postazione (PRIVATO/OPENSPACE/SALA_RIUNIONI):");
        TipoPostazione tipoPostazione = TipoPostazione.valueOf(scanner.next().toUpperCase());

        System.out.println("Inserisci la città:");
        String citta = scanner.next();

        List<Prenotazione> prenotazioniFiltrate = prenotazioniRepository.findAll().stream()
                .filter(p -> p.getPostazione().getTipo() == tipoPostazione && p.getPostazione().getEdificio().getCitta().equalsIgnoreCase(citta))
                .toList();

        System.out.println("Prenotazioni per tipo di postazione e città selezionati:");
        System.out.printf("%-15s %-20s %-20s %-20s%n", "Data", "Tipo Postazione", "Utente", "Città");
        for (Prenotazione prenotazione : prenotazioniFiltrate) {
            System.out.printf("%-15s %-20s %-20s %-20s%n", prenotazione.getDataPrenotazione(), prenotazione.getPostazione().getTipo(), prenotazione.getUtente().getNomeCompleto(), prenotazione.getPostazione().getEdificio().getCitta());
        }
    }
}
