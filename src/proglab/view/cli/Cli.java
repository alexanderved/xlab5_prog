package proglab.view.cli;

import java.io.Console;
import java.io.IOError;

import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.UnexpectedEODException;
import proglab.view.View;
import proglab.view.inputrequests.InputRequest;
import proglab.view.inputrequests.InputRequestFactory;

public class Cli extends View {
    private Console console;

    public Cli() {
        console = System.console();
    }

    @Override
    public void output(String msg) {
        console.printf(msg + "\n");
    }

    @Override
    public void error(String msg) {
        System.err.println(msg);
    }

    public String input(String msg) throws InputDeniedException, UnexpectedEODException {
        try {
            console.flush();
            
            String inputString = console.readLine(msg);
            if (inputString == null) {
                throw new UnexpectedEODException();
            }

            return inputString.trim();
        } catch (IOError e) {
            throw new InputDeniedException(e.getMessage());
        }
    }

    public String inputCommand() throws InputDeniedException, UnexpectedEODException {
        return input("> ");
    }

    @Override
    public <T> T input(InputRequest<T> req)
        throws InputDeniedException, InputFailedException, UnexpectedEODException
    {
        return req.request();
    }

    @Override
    public InputRequestFactory getInputRequestFactory() {
        return new proglab.view.cli.InputRequestFactory(this);
    }
}