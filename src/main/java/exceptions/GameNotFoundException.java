package exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String msg) {
        super(msg);
    }
}
