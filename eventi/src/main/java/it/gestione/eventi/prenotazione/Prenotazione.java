package it.gestione.eventi.prenotazione;

import it.gestione.eventi.auth.AppUser;
import it.gestione.eventi.evento.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "prenotazioni")
public class Prenotazione {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn (name = "utente_id", nullable = false)
	private AppUser utente;

	@ManyToOne
	@JoinColumn (name = "evento_id", nullable = false)
	private Evento evento;

	private LocalDateTime dataPrenotazione = LocalDateTime.now();


}