package proglab5.view.fileinput;

import java.util.HashMap;
import java.util.Map;

import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;

class CompositeDataInputRequest<T>
        extends proglab5.view.inputrequests.CompositeDataInputRequest<T> {
    private FileInputView fileInputView;

    CompositeDataInputRequest(
            FileInputView fileInputView, String requestText, CompositeDataParser<T> parser) {
        this(fileInputView, requestText, null, parser);
    }

    CompositeDataInputRequest(
            FileInputView fileInputView, String requestText, String commentText, CompositeDataParser<T> parser) {
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
            Map<String, Object> data = new HashMap<>();
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
                throw new InputFailedException(e.getMessage());
            }
        }
    }
}
