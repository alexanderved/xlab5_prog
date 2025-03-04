package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.exceptions.RepositoryAccessDeniedException;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.exceptions.UnsupportedCommandException;

/**
 * Команда 'save', сохраняющая коллекцию.
 */
public final class SaveCommand extends Command {
    private static final String CMD_NAME = "save";
    private static final String CMD_DESCR = "сохранить коллекцию";
    private static final int NB_ARGUMENTS = 0;

    private SaveCommand(Runner runner) {
        super(runner);
    }

    @Override
    public void execute() {
        try {
            runner.getCollectionManager().save();

            runner.getView().output("Коллекция сохранена");
        } catch (RepositoryAccessDeniedException e) {
            runner.getView().error("Не удалось сохранить коллекцию: " + e.getMessage());
        }
    }

    /**
     * Парсер команды 'save'.
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

            return new SaveCommand(runner);
        }
    }
}