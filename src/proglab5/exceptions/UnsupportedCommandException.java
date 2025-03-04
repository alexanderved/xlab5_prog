package proglab5.exceptions;

public class UnsupportedCommandException extends Exception {
    private String cmd;

    public UnsupportedCommandException(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String getMessage() {
        return "Команда `" + cmd + "` не поддерживается";
    }
}