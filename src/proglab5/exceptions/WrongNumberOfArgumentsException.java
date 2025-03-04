package proglab5.exceptions;

public class WrongNumberOfArgumentsException extends Exception {
    private String cmd;
    private int nbArgsReceived;
    private int nbArgsExpected;

    public WrongNumberOfArgumentsException(
        String cmd, int nbArgsReceived, int nbArgsExpected)
    {
        this.cmd = cmd;
        this.nbArgsReceived = nbArgsReceived;
        this.nbArgsExpected = nbArgsExpected;
    }

    @Override
    public String getMessage() {
        return "Команда `" + cmd + "` получила на вход " + nbArgsReceived + " аргументов. "
            + "Ожидаемое число аргументов: " + nbArgsExpected + ".";
    }
}