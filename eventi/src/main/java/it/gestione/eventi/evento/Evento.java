package it.gestione.eventi.evento;

import it.gestione.eventi.auth.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "eventi")
public class Evento {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String titolo;

	private String descrizione;

	@Column(nullable = false)
	private LocalDate dataEvento;

	@Column(nullable = false)
	private String location;

	@Column(nullable = false)
	private int postiDisponibili;

	@ManyToOne
	@JoinColumn(name = "organizzatore_id")
	private AppUser organizzatore;



}