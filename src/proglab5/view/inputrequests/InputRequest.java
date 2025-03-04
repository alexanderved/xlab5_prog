package proglab5.view.inputrequests;

import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.view.View;

public abstract class InputRequest<T> {
    public abstract View getView();
    
    public abstract T request()
        throws InputDeniedException, InputFailedException, UnexpectedEODException;
}