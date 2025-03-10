package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда команде был передан аргумент в
 * неверном формате.
 */
public class InvalidArgumentFormatException extends Exception {
    private String cmd;
    private String arg;
    private String format;

    /**
     * @param cmd    Название команды
     * @param arg    Переданный аргумент
     * @param format Ожидаемый формат
     */
    public InvalidArgumentFormatException(String cmd, String arg, String format) {
        this.cmd = cmd;
        this.arg = arg;
        this.format = format;
    }

    @Override
    public String getMessage() {
        return "Неверный формат аргумента `" + arg + "` команды `" + cmd
                + "`. Ожидаемый формат: " + format;
    }
}