package it.gestione.eventi.evento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.gestione.eventi.auth.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {
	@NotBlank (message = "Il titolo è obbligatorio")
	private String titolo;
	private String descrizione;
	@NotNull (message = "La data è obbligatoria")
	private LocalDate dataEvento;
	@NotBlank (message = "La location è obbligatoria")
	private String location;
	@Min (value = 1, message = "Il numero di posti deve essere almeno 1")
	private int postiDisponibili;



}