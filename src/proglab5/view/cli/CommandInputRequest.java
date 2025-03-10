package proglab5.view.cli;

import proglab5.controller.Runner;
import proglab5.controller.commands.Command;
import proglab5.controller.commands.CommandParser;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.InvalidArgumentFormatException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.exceptions.WrongNumberOfArgumentsException;

class CommandInputRequest
    extends proglab5.view.inputrequests.CommandInputRequest
{
    private Cli cli;

    CommandInputRequest(Cli cli, Runner runner, CommandParser parser) {
        super(runner, parser);

        this.cli = cli;
    }

    @Override
    public Cli getView() {
        return cli;
    }

    @Override
    public Command request()
        throws InputDeniedException, InputFailedException, UnexpectedEODException
    {
        try {
            return parser.parse(cli.input("> "), runner);
        } catch (WrongNumberOfArgumentsException | InvalidArgumentFormatException
                | UnsupportedCommandException e) {
            throw new InputFailedException(e.getMessage());
        }
    }   
}
