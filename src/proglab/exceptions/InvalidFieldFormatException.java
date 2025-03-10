package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда введно поле класса в неверном
 * формате.
 */
public class InvalidFieldFormatException extends Exception {
    /**
     * Пустой конструктор.
     */
    public InvalidFieldFormatException() {
        super("Введенное поле имеет неверный формат");
    }

    /**
     * Конструктор исключения, сообщающего о поле, которое имеет неверный формат.
     * 
     * @param fieldName Название поля, имеющего неверный формат
     */
    public InvalidFieldFormatException(String fieldName) {
        super("Введенное поле `" + fieldName + "` имеет неверный формат");
    }

    /**
     * Конструктор исключения, сообщающего о поле, которое имеет неверный формат, и
     * причине возникновения ошибки.
     * 
     * @param fieldName Название поля, имеющего неверный формат
     * @param reason    Причина возникновения ошибки
     */
    public InvalidFieldFormatException(String fieldName, String reason) {
        super("Введенное поле `" + fieldName + "` имеет неверный формат: " + reason);
    }
}