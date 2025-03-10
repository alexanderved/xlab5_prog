package proglab.controller.commands;

import proglab.controller.Runner;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;
import proglab.view.View;

/**
 * Команда 'print_field_descending_annual_turnover', выводящая
 * значения поля annualTurnover всех элементов в порядке убывания.
 */
public final class PrintFieldDescendingAnnualTurnoverCommand extends Command {
    private static final String CMD_NAME = "print_field_descending_annual_turnover";
    private static final String CMD_DESCR =
            "вывести значения поля annualTurnover всех элементов в порядке убывания";
    private static final int NB_ARGUMENTS = 0;

    private PrintFieldDescendingAnnualTurnoverCommand(Runner runner) {
        super(runner);
    }

    @Override
    public void execute() {
        final View view = runner.getView();
        runner.getCollectionManager()
            .getAnnualTurnovers()
            .stream()
            .sorted((a, b) -> -a.compareTo(b))
            .map(String::valueOf)
            .forEach(view::output);
    }

    /**
     * Парсер команды 'print_field_descending_annual_turnover'.
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

            return new PrintFieldDescendingAnnualTurnoverCommand(runner);
        }
    }
}
