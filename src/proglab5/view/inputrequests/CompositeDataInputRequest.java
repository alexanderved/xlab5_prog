package proglab5.view.inputrequests;

import java.util.LinkedHashMap;
import java.util.Map;

import proglab5.exceptions.DataParserException;

public abstract class CompositeDataInputRequest<T>
        extends DataInputRequest<T> {
    protected final CompositeDataParser<T> parser;
    protected final Map<String, InputRequest<?>> fieldRequests;

    protected CompositeDataInputRequest(String requestText, CompositeDataParser<T> parser) {
        this(requestText, null, parser);
    }

    protected CompositeDataInputRequest(String requestText, String commentText, CompositeDataParser<T> parser) {
        super(requestText, commentText);

        this.parser = parser;
        this.fieldRequests = new LinkedHashMap<>();
    }

    public CompositeDataInputRequest<T> addFieldRequest(
            String fieldName, InputRequest<?> request) {
        fieldRequests.put(fieldName, request);

        return this;
    }

    @FunctionalInterface
    public interface CompositeDataParser<T> {
        T parse(Map<String, Object> data) throws DataParserException;
    }
}