package it.gestione.eventi.prenotazione;

import it.gestione.eventi.auth.AppUser;
import it.gestione.eventi.evento.Evento;
import it.gestione.eventi.evento.EventoRepository;
import it.gestione.eventi.response.GeneralResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class PrenotazioneService {
	private final PrenotazioneRepository prenotazioneRepository;

	private final EventoRepository eventoRepository;

	public GeneralResponse createPrenotazione (Long eventId, AppUser user) {
		Evento event = eventoRepository.findById(eventId)
			.orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
		// Controlla disponibilità
		if (event.getPostiDisponibili() <= 0) {
			throw new IllegalStateException("Non ci sono posti disponibili");
		}
		//non puo prenotare lo stesso evento due volte
		Optional<Prenotazione> prenotazioneEsistente = prenotazioneRepository.findByUtenteAndEvento(user, event);
		if (prenotazioneEsistente.isPresent()) {
			throw new EntityExistsException("Prenotazione già esistente per questo evento");
		}

		//un posto in meno dopo la prenotazione
		event.setPostiDisponibili(event.getPostiDisponibili() - 1);
		eventoRepository.save(event);

		Prenotazione booking = new Prenotazione();
		booking.setEvento(event);
		booking.setUtente(user);
		prenotazioneRepository.save(booking);
		GeneralResponse response = new GeneralResponse();
		response.setId(booking.getId());
		return response;
	}

	public void cancelPrenotazione (Long bookingId, AppUser user) {
		Prenotazione prenotazione = prenotazioneRepository.findById(bookingId)
			.orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));
		if (!prenotazione.getUtente().getId().equals(user.getId())) {
			throw new AccessDeniedException("Non sei autorizzato a cancellare questa prenotazione");
		}
		//aggiungo un posto all'evento
		Evento eventoConPiuPosti = prenotazione.getEvento();
		eventoConPiuPosti.setPostiDisponibili(eventoConPiuPosti.getPostiDisponibili() + 1);
		eventoRepository.save(eventoConPiuPosti);

		prenotazioneRepository.delete(prenotazione);
	}

	public List<Prenotazione> getBookingsForUser (AppUser user) {
		return prenotazioneRepository.findByUtente(user);
	}
}
