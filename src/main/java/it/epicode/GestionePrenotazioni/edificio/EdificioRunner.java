package it.epicode.GestionePrenotazioni.edificio;

import com.github.javafaker.Faker;
import it.epicode.GestionePrenotazioni.service.PostazioneEdificioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EdificioRunner implements ApplicationRunner {

    private final EdificioRepository edificioRepository;
    private final PostazioneEdificioService postazioneEdificioService;
    private final Faker faker;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        long count = edificioRepository.count();
        if (count >= 2) {
            return;
        }

        Edificio edificio = new Edificio();
        edificio.setNome(faker.company().name());
        edificio.setIndirizzo(faker.address().streetAddress());
        edificio.setCitta(faker.address().city());
        edificioRepository.save(edificio);

        Edificio edificio2 = new Edificio();
        edificio2.setNome(faker.company().name());
        edificio2.setIndirizzo(faker.address().streetAddress());
        edificio2.setCitta(faker.address().city());
        edificioRepository.save(edificio2);

        postazioneEdificioService.createPostazioniForEdificio(edificio);
        postazioneEdificioService.createPostazioniForEdificio(edificio2);
    }
}