package it.gestione.eventi.prenotazione;


import it.gestione.eventi.auth.AppUser;
import it.gestione.eventi.response.GeneralResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping ("/api/prenotazione")
@RequiredArgsConstructor
public class PrenotazioneController {
	private final PrenotazioneService prenotazioneService;

	// Prenota un evento
	@PostMapping ("/book/{eventId}")
	@ResponseStatus (HttpStatus.CREATED)
	public GeneralResponse bookEvent (@PathVariable Long eventId, Principal principal) {
		Authentication authentication = (Authentication) principal;
		AppUser user = (AppUser) authentication.getPrincipal();
		return prenotazioneService.createPrenotazione(eventId, user);
	}

	// Visualizza le prenotazioni per l'utente corrente
	@GetMapping
	public List<Prenotazione> getUserBookings (Principal principal) {
		AppUser user = (AppUser) ((Authentication) principal).getPrincipal();
		return prenotazioneService.getBookingsForUser(user);
	}

	// Annulla una prenotazione
	@DeleteMapping ("/{bookingId}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void cancelBooking (@PathVariable Long bookingId, Principal principal) {
		AppUser user = (AppUser) ((Authentication) principal).getPrincipal();
		prenotazioneService.cancelPrenotazione(bookingId, user);
	}

}