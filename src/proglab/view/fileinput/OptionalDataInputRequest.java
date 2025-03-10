package proglab.view.fileinput;

import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.UnexpectedEODException;
import proglab.view.inputrequests.DataInputRequest;

class OptionalDataInputRequest<T> extends proglab.view.inputrequests.OptionalDataInputRequest<T> {
    private FileInputView fileInputView;

    OptionalDataInputRequest(FileInputView fileInputView, DataInputRequest<T> req) {
        super(req);

        this.fileInputView = fileInputView;
    }

    @Override
    public FileInputView getView() {
        return fileInputView;
    }

    @Override
    public T request() throws InputDeniedException, InputFailedException, UnexpectedEODException {
        return fileInputView.input().equals("y") ? fileInputView.input(req) : null;
    }    
}
