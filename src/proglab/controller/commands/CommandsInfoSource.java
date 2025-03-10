package proglab.controller.commands;

/**
 * Источник информации о доступных командах.
 */
public interface CommandsInfoSource {
    /**
     * @return информацию о доступных командах
     */
    String listCommandsInfo();
}