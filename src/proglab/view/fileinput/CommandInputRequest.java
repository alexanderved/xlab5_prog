package proglab.view.fileinput;

import proglab.controller.Runner;
import proglab.controller.commands.Command;
import proglab.controller.commands.CommandParser;
import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.InvalidArgumentFormatException;
import proglab.exceptions.UnexpectedEODException;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;

class CommandInputRequest
    extends proglab.view.inputrequests.CommandInputRequest
{
    private FileInputView fileInputView;

    CommandInputRequest(FileInputView fileInputView, Runner runner, CommandParser parser) {
        super(runner, parser);

        this.fileInputView = fileInputView;
    }

    @Override
    public FileInputView getView() {
        return fileInputView;
    }

    @Override
    public Command request()
        throws InputDeniedException, InputFailedException, UnexpectedEODException
    {
        try {
            return parser.parse(fileInputView.input(), runner);
        } catch (WrongNumberOfArgumentsException | InvalidArgumentFormatException
                | UnsupportedCommandException e) {
            throw new InputFailedException(e.getMessage());
        }
    }   
}
