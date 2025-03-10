package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда команда получила неверное число
 * аргументов.
 */
public class WrongNumberOfArgumentsException extends Exception {
    private String cmd;
    private int nbArgsReceived;
    private int nbArgsExpected;

    /**
     * @param cmd            Название команды
     * @param nbArgsReceived Число полученных аргументов
     * @param nbArgsExpected Ожидаемое число аргументов
     */
    public WrongNumberOfArgumentsException(
            String cmd, int nbArgsReceived, int nbArgsExpected) {
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