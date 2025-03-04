package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.InvalidArgumentFormatException;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.utils.OrganizationTemplateCollector;

/**
 * Команда 'update', обновляющая значение элемента коллекции, id которого равен заданному.
 */
public class UpdateCommand extends Command {
    private static final String CMD_NAME = "update";
    private static final String CMD_ARG_FORMAT = "id {element}";
    private static final String CMD_DESCR =
        "обновить значение элемента коллекции, id которого равен заданному";
    private static final int NB_ARGUMENTS = 1;

    private final int id;
    private final OrganizationTemplate template;

    private UpdateCommand(int id, OrganizationTemplate template, Runner runner) {
        super(runner);

        if (template == null) {
            throw new IllegalArgumentException("Поле `template` не может быть null");
        }

        this.id = id;
        this.template = template;
    }
    
    @Override
    public void execute() {
        if (runner.getCollectionManager().update(id, template)) {
            runner.getView().output("Информация организации с ID " + id + " обновлена");
        } else {
            runner.getView().output("Не удалось найти организацию с ID " + id);
        }
    }

    /**
     * Парсер команды 'update'.
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
            UnsupportedCommandException,
            UnexpectedEODException
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

            OrganizationTemplate template =
                OrganizationTemplateCollector.collect(runner.getView());
            if (template == null) { 
                return null;
            }

            return new UpdateCommand(id, template, runner);
        }
    }
}
