package proglab.view.cli;

import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.UnexpectedEODException;
import proglab.view.inputrequests.DataInputRequest;

class OptionalDataInputRequest<T> extends proglab.view.inputrequests.OptionalDataInputRequest<T> {
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
