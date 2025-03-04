package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.exceptions.UnsupportedCommandException;

/**
 * Команда 'show', выводящая в стандартный поток вывода
 * все элементы коллекции в строковом представлении.
 */
public final class ShowCommand extends Command {
    private static final String CMD_NAME = "show";
    private static final String CMD_DESCR = "вывести в стандартный поток вывода"
            + " все элементы коллекции в строковом представлении";
    private static final int NB_ARGUMENTS = 0;

    private ShowCommand(Runner runner) {
        super(runner);
    }

    @Override
    public void execute() {
        runner.getView().output(runner.getCollectionManager().toString());
    }

    /**
     * Парсер команды 'show'.
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

            return new ShowCommand(runner);
        }
    }
}