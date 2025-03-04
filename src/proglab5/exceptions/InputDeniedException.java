package proglab5.exceptions;

public class InputDeniedException extends Exception {
    public InputDeniedException(String message) {
        super(message);
    }
    
    @Override
    public String getMessage() {
        return "Запрос на ввод к представлению отклонен: " + super.getMessage();
    }
}