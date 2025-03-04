package proglab5.view.fileinput;

import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.view.inputrequests.DataInputRequest;

class OptionalDataInputRequest<T> extends proglab5.view.inputrequests.OptionalDataInputRequest<T> {
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
