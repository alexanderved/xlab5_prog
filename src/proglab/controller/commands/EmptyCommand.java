package proglab.controller.commands;

import proglab.controller.Runner;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;

/**
 * Команды, вызываемая при вводе пустой строки, при которой не выполняется никакая операция.
 */
public final class EmptyCommand extends Command {
    private static final String CMD_NAME = "";
    private static final int NB_ARGUMENTS = 0;

    private EmptyCommand(Runner runner) {
        super(runner);
    }

    @Override
    public void execute() {}

    /**
     * Парсер пустой команды.
     */
    public static final class Parser extends CommandParser {
        @Override
        public String listCommandsInfo() {
            return null;
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

            return new EmptyCommand(runner);
        }
    }
}