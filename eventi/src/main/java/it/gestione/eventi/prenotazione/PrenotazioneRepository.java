package it.gestione.eventi.prenotazione;


import it.gestione.eventi.auth.AppUser;
import it.gestione.eventi.evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
	List<Prenotazione> findByUtente(AppUser utente);
	Optional<Prenotazione> findByUtenteAndEvento(AppUser utente, Evento evento);
}
