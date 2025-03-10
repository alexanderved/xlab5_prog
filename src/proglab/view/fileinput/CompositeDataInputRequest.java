package proglab.view.fileinput;

import java.util.HashMap;
import java.util.Map;

import proglab.exceptions.DataParserException;
import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.UnexpectedEODException;

class CompositeDataInputRequest<E, T>
        extends proglab.view.inputrequests.CompositeDataInputRequest<E, T> {
    private FileInputView fileInputView;

    CompositeDataInputRequest(
            FileInputView fileInputView, String requestText, CompositeDataParser<E, T> parser) {
        this(fileInputView, requestText, null, parser);
    }

    CompositeDataInputRequest(
            FileInputView fileInputView, String requestText, String commentText, CompositeDataParser<E, T> parser) {
        super(requestText, commentText, parser);

        this.fileInputView = fileInputView;
    }

    @Override
    public FileInputView getView() {
        return fileInputView;
    }

    @Override
    public T request() throws InputDeniedException, InputFailedException, UnexpectedEODException {
        while (true) {
            Map<E, Object> data = new HashMap<>();
            for (var entry : fieldRequests.entrySet()) {
                try {
                    data.put(entry.getKey(), entry.getValue().request());
                } catch (InputFailedException e) {
                    fileInputView.error("Не удалось получить поле `"
                            + entry.getKey() + "`: " + e.getMessage());
                }
            }

            try {
                return parser.parse(data);
            } catch (DataParserException e) {
                throw new InputFailedException(e);
            }
        }
    }
}
