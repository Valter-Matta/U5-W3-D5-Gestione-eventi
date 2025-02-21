package it.gestione.eventi.evento;


import it.gestione.eventi.auth.AppUser;
import it.gestione.eventi.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/evento")
@RequiredArgsConstructor

public class EventoController {
	private final EventoService EventoService;

	@GetMapping
	public List<EventoResponse> findAll () {
		return EventoService.getAllEvents();
	}

	@GetMapping ("/{id}")
	public EventoResponse findById (@PathVariable Long id) {
		return EventoService.getEventById(id).map(EventoService::fromEvento).orElse(null);
	}

	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)
	public GeneralResponse save (@RequestBody EventoRequest request, @AuthenticationPrincipal AppUser organizer) {
		return EventoService.createEvent(request, organizer);
	}

	@PutMapping ("/{id}")
	public EventoResponse update (@PathVariable Long id, @RequestBody EventoRequest request, @AuthenticationPrincipal AppUser organizer) {
		return EventoService.updateEvent(id, request, organizer);
	}

	@DeleteMapping ("/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete (@PathVariable Long id, @AuthenticationPrincipal AppUser organizer) {
		EventoService.deleteEvent(id, organizer);
		return ResponseEntity.noContent().build();
	}

}