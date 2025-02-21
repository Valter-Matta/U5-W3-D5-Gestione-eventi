package it.gestione.eventi.prenotazione;

import it.gestione.eventi.auth.AppUser;
import it.gestione.eventi.evento.Evento;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {

	private Evento eventoId;
}