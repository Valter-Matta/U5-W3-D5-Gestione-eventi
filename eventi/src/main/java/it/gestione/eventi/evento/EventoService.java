package it.gestione.eventi.evento;

import it.gestione.eventi.auth.AppUser;
import it.gestione.eventi.response.GeneralResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
public class EventoService {
	@Autowired
	private EventoRepository eventRepository;

	public GeneralResponse createEvent (@Valid EventoRequest event, AppUser organizer) {

		Evento entity = fromEventoRequest(event);
		entity.setOrganizzatore(organizer);
		eventRepository.save(entity);
		GeneralResponse response = new GeneralResponse();
		response.setId(entity.getId());
		return response;
	}

	public EventoResponse updateEvent (Long id, @Valid EventoRequest eventoUpdate, AppUser organizer) {
		Evento event = eventRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
		// Verifico che l'organizzatore corrente sia il proprietario dell'evento
		if (!event.getOrganizzatore().getId().equals(organizer.getId())) {
			throw new AccessDeniedException("Non sei autorizzato ad aggiornare questo evento");
		}

		BeanUtils.copyProperties(eventoUpdate, event);
		eventRepository.save(event);
		EventoResponse response = new EventoResponse();
		BeanUtils.copyProperties(event, response);
		return response;
	}

	public void deleteEvent (Long id, AppUser organizer) {
		Evento event = eventRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
		if (!event.getOrganizzatore().getId().equals(organizer.getId())) {
			throw new AccessDeniedException("Non sei autorizzato ad eliminare questo evento");
		}
		eventRepository.delete(event);
	}

	public List<EventoResponse> getAllEvents () {
		EventoResponse response = new EventoResponse();
		return eventRepository.findAll().stream().map(this::fromEvento).toList();
	}

	public Optional<Evento> getEventById (Long id) {
		return eventRepository.findById(id);
	}

	public Evento fromEventoRequest (EventoRequest evento) {
		Evento e = new Evento();
		e.setTitolo(evento.getTitolo());
		e.setDescrizione(evento.getDescrizione());
		e.setDataEvento(evento.getDataEvento());
		e.setLocation(evento.getLocation());
		e.setPostiDisponibili(evento.getPostiDisponibili());
		return e;
	}

	public EventoResponse fromEvento (Evento e) {
		EventoResponse request = new EventoResponse();
		request.setTitolo(e.getTitolo());
		request.setDescrizione(e.getDescrizione());
		request.setDataEvento(e.getDataEvento());
		request.setLocation(e.getLocation());
		request.setPostiDisponibili(e.getPostiDisponibili());
		return request;
	}
}
