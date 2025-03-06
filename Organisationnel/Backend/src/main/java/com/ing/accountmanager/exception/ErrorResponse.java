package com.ing.accountmanager.exception;

public class ErrorResponse {

    // Code de statut HTTP
    private int statusCode;

    // Message d'erreur
    private String message;

    // Constructeur
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // Getters et Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
