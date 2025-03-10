package proglab.view.inputrequests;

import proglab.exceptions.DataParserException;

public abstract class PrimitiveDataInputRequest<T> extends DataInputRequest<T> {
    protected final PrimitiveDataParser<T> parser;

    protected PrimitiveDataInputRequest(String requestText, PrimitiveDataParser<T> parser) {
        this(requestText, null, parser);
    }

    protected PrimitiveDataInputRequest(String requestText, String commentText, PrimitiveDataParser<T> parser) {
        super(requestText, commentText);

        this.parser = parser;
    }

    @FunctionalInterface
    public interface PrimitiveDataParser<T> {
        T parse(String data) throws DataParserException;
    }
}