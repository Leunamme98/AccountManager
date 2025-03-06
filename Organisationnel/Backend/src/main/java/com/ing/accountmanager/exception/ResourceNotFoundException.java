package com.ing.accountmanager.exception;

public class ResourceNotFoundException extends RuntimeException {

    // Constructeur de l'exception avec un message
    public ResourceNotFoundException(String message) {
        // Passe le message à la classe parente
        super(message);
    }
}
