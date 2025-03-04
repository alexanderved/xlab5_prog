package proglab5.exceptions;

public class InvalidArgumentFormatException extends Exception {
    private String cmd;
    private String arg;
    private String format;

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