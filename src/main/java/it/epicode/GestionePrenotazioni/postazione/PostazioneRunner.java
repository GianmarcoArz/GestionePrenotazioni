package it.epicode.GestionePrenotazioni.postazione;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostazioneRunner implements ApplicationRunner {

    private final PostazioneRepository postazioneRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}