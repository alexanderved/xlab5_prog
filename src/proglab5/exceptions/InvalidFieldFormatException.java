package proglab5.exceptions;

public class InvalidFieldFormatException extends Exception {
    public InvalidFieldFormatException() {
        super("Введенное поле имеет неверный формат");
    }

    public InvalidFieldFormatException(String fieldName) {
        super("Введенное поле `" + fieldName + "` имеет неверный формат");
    }

    public InvalidFieldFormatException(String fieldName, String reason) {
        super("Введенное поле `" + fieldName + "` имеет неверный формат: " + reason);
    }
}