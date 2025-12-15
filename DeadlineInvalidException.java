public class DeadlineInvalidException extends Exception {

    // Constructor dengan pesan custom
    public DeadlineInvalidException(String message) {
        super(message);
    }

    // Constructor dengan pesan dan penyebab (cause)
    public DeadlineInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor hanya dengan penyebab
    public DeadlineInvalidException(Throwable cause) {
        super(cause);
    }
}