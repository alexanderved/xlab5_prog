package proglab.view.fileinput;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.UnexpectedEODException;
import proglab.view.View;
import proglab.view.inputrequests.InputRequest;
import proglab.view.inputrequests.InputRequestFactory;

public class FileInputView extends View implements Closeable {
    View view;
    BufferedReader reader;

    public FileInputView(String fileName, View view) throws FileNotFoundException {
        this.view = view;
        reader = new BufferedReader(new FileReader(fileName));
    }

    @Override
    public void output(String msg) {
        view.output(msg);
    }

    @Override
    public void error(String msg) {
        view.error(msg);
    }

    @Override
    public String input(String msg) throws InputDeniedException, UnexpectedEODException {
        String line = null;

        try {
            line = reader.readLine();
        } catch (EOFException e) {
            line = null;
        } catch (IOException e) {
            throw new InputDeniedException(e.getMessage());
        }

        if (line == null) {
            throw new UnexpectedEODException();
        }

        return line;
    }

    @Override
    public <T> T input(InputRequest<T> req)
        throws InputDeniedException, InputFailedException, UnexpectedEODException
    {
        return req.request();
    }

    @Override
    public InputRequestFactory getInputRequestFactory() {
        return new proglab.view.fileinput.InputRequestFactory(this);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}