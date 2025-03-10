package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда парсер команд получил строку,
 * которую невозможно распарсить ни в одну из доступных ему команд.
 */
public class UnsupportedCommandException extends Exception {
    private String cmd;

    /**
     * @param cmd Название команды, полученной парсером.
     */
    public UnsupportedCommandException(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String getMessage() {
        return "Команда `" + cmd + "` не поддерживается";
    }
}