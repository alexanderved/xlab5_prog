package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.application.CollectionManager;
import proglab5.view.View;

/**
 * Команда 'average_of_employees_count', выводящая среднее значение поля employeesCount для всех элементов коллекции.
 */
public final class AverageOfEmployeesCountCommand extends Command {
    private static final String CMD_NAME = "average_of_employees_count";
    private static final String CMD_DESCR =
            "вывести среднее значение поля employeesCount для всех элементов коллекции";
    private static final int NB_ARGUMENTS = 0;

    private AverageOfEmployeesCountCommand(Runner runner) {
        super(runner);
    }

    @Override
    public void execute() {
        final View view = runner.getView();
        final CollectionManager collectionManager = runner.getCollectionManager();

        view.output("Среднее число сотрудников в организациях: "
            + collectionManager.averageOfEmployeesCount());
    }

    /**
     * Парсер команды 'average_of_employees_count'.
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

            return new AverageOfEmployeesCountCommand(runner);
        }
    }
}
