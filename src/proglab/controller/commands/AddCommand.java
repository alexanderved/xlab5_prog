package proglab.controller.commands;

import proglab.controller.Runner;
import proglab.domain.OrganizationTemplate;
import proglab.exceptions.UnexpectedEODException;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;
import proglab.utils.OrganizationTemplateCollector;

/**
 * Команда 'add', добавляющая новый элемент в коллекцию.
 */
public class AddCommand extends Command {
    private static final String CMD_NAME = "add";
    private static final String CMD_ARG_FORMAT = "{element}";
    private static final String CMD_DESCR = "добавить новый элемент в коллекцию";
    private static final int NB_ARGUMENTS = 0;

    private final OrganizationTemplate template;

    private AddCommand(OrganizationTemplate template, Runner runner) {
        super(runner);

        if (template == null) {
            throw new IllegalArgumentException("Поле `template` не может быть null");
        }

        this.template = template;
    }

    @Override
    public void execute() {
        runner.getCollectionManager().add(template);

        runner.getView().output("Новая организация добавлена в коллекцию");
    }
    
    /**
     * Парсер команды 'add'.
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

            return new AddCommand(template, runner);
        }
    }
}
