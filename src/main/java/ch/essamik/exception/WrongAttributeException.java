package ch.essamik.exception;

public class WrongAttributeException extends Exception {

    private static final long serialVersionUID = 563791221563990135L;

    public WrongAttributeException(String message) {
        super(message);
    }
}
