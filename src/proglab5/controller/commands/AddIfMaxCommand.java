package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.domain.Organization;
import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.utils.OrganizationTemplateCollector;

/**
 * Команда 'add_if_max', добавляющая новый элемент в коллекцию,
 * если его значение превышает значение наибольшего элемента этой коллекции.
 */
public class AddIfMaxCommand extends Command {
    private static final String CMD_NAME = "add_if_max";
    private static final String CMD_ARG_FORMAT = "{element}";
    private static final String CMD_DESCR = "добавить новый элемент в коллекцию,"
        + " если его значение превышает значение наибольшего элемента этой коллекции";
    private static final int NB_ARGUMENTS = 0;

    private final OrganizationTemplate template;

    private AddIfMaxCommand(OrganizationTemplate template, Runner runner) {
        super(runner);

        if (template == null) {
            throw new IllegalArgumentException("Поле `template` не может быть null");
        }

        this.template = template;
    }

    @Override
    public void execute() {
        Organization maxOrg = runner.getCollectionManager().max();
        if (template.compareTo(maxOrg) > 0) {
            runner.getCollectionManager().add(template);

            runner.getView().output("Новая организация добавлена в коллекцию");
        } else {
            runner.getView().output("Новая организация не добавлена в коллекцию, так как"
                + " ее значение не превышает значение наибольшего элемента коллекции");
        }
    }
    
    /**
     * Парсер команды 'add_if_max'.
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

            return new AddIfMaxCommand(template, runner);
        }
    }
}
