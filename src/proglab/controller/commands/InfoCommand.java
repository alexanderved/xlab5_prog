package proglab.controller.commands;

import proglab.application.CollectionManager;
import proglab.controller.Runner;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;
import proglab.view.View;

/**
 * Команда 'info', выводящая в стандартный поток вывода информацию о коллекции.
 */
public final class InfoCommand extends Command {
    private static final String CMD_NAME = "info";
    private static final String CMD_DESCR =
            "вывести в стандартный поток вывода информацию о коллекции";
    private static final int NB_ARGUMENTS = 0;

    private InfoCommand(Runner runner) {
        super(runner);
    }

    @Override
    public void execute() {
        final View view = runner.getView();
        final CollectionManager collectionManager = runner.getCollectionManager();

        view.output("Информация о коллекции:");
        view.output("\tТип: " + collectionManager.getCollectionType());
        view.output("\tКоличество элементов: " + collectionManager.getCollectionSize());
        view.output("\tДата инициализации: " + collectionManager.getInitDate());
        view.output("\tДата модификации: " + collectionManager.getLastModDate());
    }

    /**
     * Парсер команды 'info'.
     */
    public static final class Parser extends CommandParser {
        private static final String CMD_INFO = CMD_NAME
                + ": " + CMD_DESCR;

        @Override
        public String listCommandsInfo() {
            return CMD_INFO;
        }

        @Override
        public Command parse(String cmd, Runner runner)
        throws
            WrongNumberOfArgumentsException,
            UnsupportedCommandException
        {
            String[] args = cmd.split(" ");
            if (!args[0].equals(CMD_NAME)) {
                throw new UnsupportedCommandException(args[0]);
            }

            if (args.length - 1 != NB_ARGUMENTS) {
                throw new WrongNumberOfArgumentsException(args[0],
                        args.length - 1, NB_ARGUMENTS);
            }

            return new InfoCommand(runner);
        }
    }
}