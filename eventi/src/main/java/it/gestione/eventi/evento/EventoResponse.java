package it.gestione.eventi.evento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.gestione.eventi.auth.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponse {

	private Long id;
	private String titolo;
	private String descrizione;
	private LocalDate dataEvento;
	private String location;
	private int postiDisponibili;
	private AppUser organizzatore;


}
