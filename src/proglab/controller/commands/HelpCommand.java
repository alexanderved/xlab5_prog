package proglab.controller.commands;

import proglab.controller.Runner;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;

/**
 * Команда 'help', выводящая справку по доступным командам.
 */
public final class HelpCommand extends Command {
    private static final String CMD_NAME = "help";
    private static final String CMD_DESCR = "вывести справку по доступным командам";
    private static final int NB_ARGUMENTS = 0;

    private HelpCommand(Runner runner) {
        super(runner);
    }

    @Override
    public void execute() {
        runner.getView().output(runner.getCommandParser().listCommandsInfo());
    }

    /**
     * Парсер команды 'help'.
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

            return new HelpCommand(runner);
        }
    }
}