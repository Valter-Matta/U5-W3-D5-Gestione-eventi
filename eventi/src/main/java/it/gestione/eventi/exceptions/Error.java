package it.gestione.eventi.exceptions;

import lombok.Data;

// risposta di errore
// da inserire in un ErrorHandler
@Data
public class Error {
    private String message;
    private String details;
    private String status;
}
