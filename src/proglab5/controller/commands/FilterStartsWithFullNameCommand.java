package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.exceptions.InvalidArgumentFormatException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.exceptions.WrongNumberOfArgumentsException;

/**
 * Команда 'filter_starts_with_full_name', выводящая элементы, значение поля
 * fullName которых начинается с заданной подстроки.
 */
public class FilterStartsWithFullNameCommand extends Command {
    private static final String CMD_NAME = "filter_starts_with_full_name";
    private static final String CMD_ARG_FORMAT = "fullName";
    private static final String CMD_DESCR =
        "вывести элементы, значение поля fullName которых начинается с заданной подстроки";
    private static final int NB_ARGUMENTS = 1;

    private final String fullName;

    private FilterStartsWithFullNameCommand(String fullName, Runner runner) {
        super(runner);

        this.fullName = fullName;
    }
    
    @Override
    public void execute() {
        runner.getView().output(
            runner.getCollectionManager().toStringFilteredFullName(fullName));
    }

    /**
     * Парсер команды 'filter_starts_with_full_name'.
     */
    public static final class Parser extends CommandParser {
        private static final String CMD_INFO = CMD_NAME
                + " " + CMD_ARG_FORMAT + ": " + CMD_DESCR;

        @Override
        public String listCommandsInfo() {
            return CMD_INFO;
        }

        @Override
        public Command parse(String cmd, Runner runner)
        throws
            WrongNumberOfArgumentsException,
            InvalidArgumentFormatException,
            UnsupportedCommandException
        {
            String[] args = cmd.split(" ");
            if (!args[0].equals(CMD_NAME)) {
                throw new UnsupportedCommandException(args[0]);
            }

            if (args.length - 1 != NB_ARGUMENTS) {
                throw new WrongNumberOfArgumentsException(
                    args[0], args.length - 1, NB_ARGUMENTS);
            }

            return new FilterStartsWithFullNameCommand(args[1], runner);
        }
    }
}
