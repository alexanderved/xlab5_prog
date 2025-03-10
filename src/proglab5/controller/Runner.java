package proglab5.controller;

import proglab5.application.CollectionManager;
import proglab5.controller.commands.Command;
import proglab5.controller.commands.CommandParser;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.view.View;
import proglab5.view.inputrequests.CommandInputRequest;
import proglab5.view.inputrequests.InputRequestFactory;

/**
 * Исполнитель команд поступающих от пользователя.
 */
public class Runner {
    private State state = State.STOPPED;
    private View view;
    private CollectionManager collectionManager;
    private CommandParser cmdParser;

    /**
     * @param view Представление, из которого поступают команды и данные
     * @param collectionManager Менеджер коллекции организаций 
     * @param cmdParser Парсер команд пользователя
     * @see View
     * @see CollectionManager
     * @see CommandParser
     */
    public Runner(View view, CollectionManager collectionManager,
            CommandParser cmdParser) {
        if (view == null) {
            throw new IllegalArgumentException(
                    "`view` в классе `Runner` не должно быть null");
        }
        this.view = view;

        if (collectionManager == null) {
            throw new IllegalArgumentException(
                    "`collectionManager` в классе `Runner` не должно быть null");
        }
        this.collectionManager = collectionManager;

        if (cmdParser == null) {
            throw new IllegalArgumentException(
                    "`cmdParser` в классе `Runner` не должно быть null");
        }
        this.cmdParser = cmdParser;
    }

    /**
     * @return представление, из которого поступают команды
     * @see View
     */
    public View getView() {
        return view;
    }

    /**
     * Устанавливает представление, из которого поступают команды
     * @param view представление, из которого поступают команды
     * @see View
     */
    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException(
                    "`view` в классе `Runner` не должно быть null");
        }

        this.view = view;
    }

    /**
     * @return менеджер коллекции организаций
     * @see CollectionManager
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Устанавливает менеджер коллекции организаций
     * @param collectionManager менеджер коллекции организаций
     */
    public void setCollectionManager(CollectionManager collectionManager) {
        if (collectionManager == null) {
            throw new IllegalArgumentException(
                    "`collectionManager` в классе `Runner` не должно быть null");
        }
        this.collectionManager = collectionManager;
    }

    /**
     * @return парсер команд пользователя
     */
    public CommandParser getCommandParser() {
        return cmdParser;
    }

    /**
     * Устанавливает парсер команд пользователя.
     * @param cmdParser Парсер команд
     */
    public void setCommandParser(CommandParser cmdParser) {
        if (cmdParser == null) {
            throw new IllegalArgumentException(
                    "`cmdParser` в классе `Runner` не должно быть null");
        }
        this.cmdParser = cmdParser;
    }

    /**
     * Цикл исполнения программы.
     */
    public void run() {
        setState(State.RUNNING);

        InputRequestFactory inReqFactory = view.getInputRequestFactory();
        while (getState() == State.RUNNING) {
            try {
                CommandInputRequest cmdInReq = inReqFactory.createCommandInputRequest(this, cmdParser);
                Command cmd = view.input(cmdInReq);

                if (cmd == null) {
                    continue;
                }

                cmd.execute();
            } catch (InputFailedException e) {
                view.error(e.getMessage());
            } catch (InputDeniedException e) {
                view.error("Не удалось прочитать команду: " + e.getMessage());

                stop();
            } catch (UnexpectedEODException e) {
                stop();
            }
        }
    }

    /**
     * Останавливает выполнение программы.
     */
    public void stop() {
        setState(State.STOPPED);
    }

    private State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    private enum State {
        STOPPED, RUNNING;
    }
}