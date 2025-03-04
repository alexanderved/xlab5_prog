package proglab5.view.fileinput;

import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;

final class PrimitiveDataInputRequest<T>
        extends proglab5.view.inputrequests.PrimitiveDataInputRequest<T> {
    private FileInputView fileInputView;

    PrimitiveDataInputRequest(
            FileInputView fileInputView, String requestText, PrimitiveDataParser<T> parser) {
        this(fileInputView, requestText, null, parser);
    }

    PrimitiveDataInputRequest(
            FileInputView fileInputView, String requestText, String commentText, PrimitiveDataParser<T> parser) {
        super(requestText, commentText, parser);

        this.fileInputView = fileInputView;
    }

    @Override
    public FileInputView getView() {
        return fileInputView;
    }

    @Override
    public T request() throws InputDeniedException, InputFailedException, UnexpectedEODException {
        try {
            String fieldString = fileInputView.input();
            fieldString = fieldString.isEmpty() ? null : fieldString;

            return parser.parse(fieldString);
        } catch (DataParserException e) {
            throw new InputFailedException(e.getMessage());
        }
    }
}
