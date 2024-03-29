package org.lpzneider.veterinaria.exceptions;

public class ServiceJpaException extends RuntimeException {
    public ServiceJpaException(String message) {
        super(message);
    }

    public ServiceJpaException(String message, Throwable cause) {
        super(message, cause);
    }
}
