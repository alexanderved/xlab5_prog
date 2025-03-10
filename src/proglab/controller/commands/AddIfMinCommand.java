package proglab.controller.commands;

import proglab.controller.Runner;
import proglab.domain.Organization;
import proglab.domain.OrganizationTemplate;
import proglab.exceptions.UnexpectedEODException;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;
import proglab.utils.OrganizationTemplateCollector;

/**
 * Команда 'add_if_min', добавляющая новый элемент в коллекцию,
 * если его значение меньше, чем у наименьшего элемента этой коллекции.
 */
public class AddIfMinCommand extends Command {
    private static final String CMD_NAME = "add_if_min";
    private static final String CMD_ARG_FORMAT = "{element}";
    private static final String CMD_DESCR = "добавить новый элемент в коллекцию,"
        + " если его значение меньше, чем у наименьшего элемента этой коллекции";
    private static final int NB_ARGUMENTS = 0;

    private final OrganizationTemplate template;

    private AddIfMinCommand(OrganizationTemplate template, Runner runner) {
        super(runner);

        if (template == null) {
            throw new IllegalArgumentException("Поле `template` не может быть null");
        }

        this.template = template;
    }

    @Override
    public void execute() {
        Organization minOrg = runner.getCollectionManager().min();
        if (template.compareTo(minOrg) < 0) {
            runner.getCollectionManager().add(template);

            runner.getView().output("Новая организация добавлена в коллекцию");
        } else {
            runner.getView().output("Новая организация не добавлена в коллекцию, так как"
                + " ее значение не меньше значения наименьшего элемента коллекции");
        }
    }
    
    /**
     * Парсер команды 'add_if_min'.
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

            OrganizationTemplate template =
                OrganizationTemplateCollector.collect(runner.getView());
            if (template == null) { 
                return null;
            }

            return new AddIfMinCommand(template, runner);
        }
    }
}
