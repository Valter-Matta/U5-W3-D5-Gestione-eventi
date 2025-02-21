package it.gestione.eventi.evento;


import it.gestione.eventi.auth.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
	List<Evento> findByOrganizzatore (AppUser organizzatore);
}
