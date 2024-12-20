package it.epicode.GestionePrenotazioni.utente;

import it.epicode.GestionePrenotazioni.postazione.PostazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UtenteRunner implements ApplicationRunner {

   private final UtenteRepository utenteRepository;
   private final PostazioneRepository postazioneRepository;
    private final Utente utente;
    private final Utente utenteAlpha;
    private final Utente utenteBravo;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = utenteRepository.count();
        if (count >= 3) {
            return;
        }

        utenteRepository.save(utente);
        utenteRepository.save(utenteAlpha);
        utenteRepository.save(utenteBravo);
    }
}
