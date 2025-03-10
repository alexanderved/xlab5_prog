package proglab5.view.cli;

import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.view.inputrequests.DataInputRequest;

class OptionalDataInputRequest<T> extends proglab5.view.inputrequests.OptionalDataInputRequest<T> {
    private Cli cli;

    OptionalDataInputRequest(Cli cli, DataInputRequest<T> req) {
        super(req);

        this.cli = cli;
    }

    @Override
    public Cli getView() {
        return cli;
    }

    @Override
    public T request() throws InputDeniedException, InputFailedException, UnexpectedEODException {
        String ans = cli.input("Желаете указать " + req.getRequestText() + " (y/n[default])? ");
        
        return ans.equals("y") ? cli.input(req) : null;
    }    
}
