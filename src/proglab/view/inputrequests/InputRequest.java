package proglab.view.inputrequests;

import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.UnexpectedEODException;
import proglab.view.View;

public abstract class InputRequest<T> {
    public abstract View getView();
    
    public abstract T request()
        throws InputDeniedException, InputFailedException, UnexpectedEODException;
}