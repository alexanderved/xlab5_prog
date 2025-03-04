package proglab5.view;

import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.view.inputrequests.InputRequest;
import proglab5.view.inputrequests.InputRequestFactory;

public abstract class View {
    public abstract void output(String msg);

    public abstract void error(String msg);

    public abstract String input(String msg)
            throws InputDeniedException, UnexpectedEODException;

    public abstract <T> T input(InputRequest<T> req)
            throws InputDeniedException, InputFailedException, UnexpectedEODException;

    public abstract InputRequestFactory getInputRequestFactory();

    public String input()
            throws InputDeniedException, UnexpectedEODException {
        return input((String)null);
    }
}