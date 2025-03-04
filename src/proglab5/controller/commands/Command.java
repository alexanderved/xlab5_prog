package proglab5.controller.commands;

import proglab5.controller.Runner;

/**
 * Абстрактная команда
 */
public abstract class Command {
    /**
     * Исполнитель, который выполняет данную команду.
     */
    protected final Runner runner;

    /**
     * @param runner Исполнитель, который выполняет данную команду
     * @see Runner
     */
    protected Command(Runner runner) {
        if (runner == null) {
            throw new IllegalArgumentException(
                "Поле `runner` класса `Command` не может быть null");
        }

        this.runner = runner;
    }

    /**
     * Исполняет команду.
     */
    public abstract void execute();
}