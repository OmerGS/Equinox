package exceptions;

public class NotValidPortNumber extends Exception {
    public NotValidPortNumber(String errorMessage) {
        super(errorMessage);
    }
}
