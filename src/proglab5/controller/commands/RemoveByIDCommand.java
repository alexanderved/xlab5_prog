package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.exceptions.InvalidArgumentFormatException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.exceptions.WrongNumberOfArgumentsException;

/**
 * Команда 'remove_by_id', удаляющая элемент из коллекции по его id.
 */
public class RemoveByIDCommand extends Command {
    private static final String CMD_NAME = "remove_by_id";
    private static final String CMD_ARG_FORMAT = "id";
    private static final String CMD_DESCR = "удалить элемент из коллекции по его id";
    private static final int NB_ARGUMENTS = 1;

    private final int id;

    private RemoveByIDCommand(int id, Runner runner) {
        super(runner);

        this.id = id;
    }
    
    @Override
    public void execute() {
        if (runner.getCollectionManager().removeById(id)) {
            runner.getView().output("Информация об организации с ID " + id + " удалена");
        } else {
            runner.getView().output("Не удалось найти организацию с ID " + id);
        }
    }

    /** 
     * Парсер команды 'remove_by_id'.
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
                throw new WrongNumberOfArgumentsException(args[0],
                        args.length - 1, NB_ARGUMENTS);
            }

            int id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException | NullPointerException e) {
                throw new InvalidArgumentFormatException(CMD_NAME, "id", "Целое число");
            }

            return new RemoveByIDCommand(id, runner);
        }
    }
}
