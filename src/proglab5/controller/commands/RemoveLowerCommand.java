package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.utils.OrganizationTemplateCollector;

/**
 * Команда 'remove_lower', удаляющая из коллекции все элементы, меньшие, чем заданный.
 */
public class RemoveLowerCommand extends Command {
    private static final String CMD_NAME = "remove_lower";
    private static final String CMD_ARG_FORMAT = "{element}";
    private static final String CMD_DESCR =
        "удалить из коллекции все элементы, меньшие, чем заданный";
    private static final int NB_ARGUMENTS = 0;

    private final OrganizationTemplate template;

    private RemoveLowerCommand(OrganizationTemplate template, Runner runner) {
        super(runner);

        if (template == null) {
            throw new IllegalArgumentException("Поле `template` не может быть null");
        }

        this.template = template;
    }

    @Override
    public void execute() {
        runner.getCollectionManager().removeLower(template);

        runner.getView().output("Удалены все организации, меньшие, чем заданная");
    }
    
    /**
     * Парсер команды 'remove_lower'.
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

            return new RemoveLowerCommand(template, runner);
        }
    }
}
