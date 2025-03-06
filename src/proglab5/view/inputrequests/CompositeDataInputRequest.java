package proglab5.view.inputrequests;

import java.util.LinkedHashMap;
import java.util.Map;

import proglab5.exceptions.DataParserException;

public abstract class CompositeDataInputRequest<E, T>
        extends DataInputRequest<T> {
    protected final CompositeDataParser<E, T> parser;
    protected final Map<E, InputRequest<?>> fieldRequests;

    protected CompositeDataInputRequest(String requestText, CompositeDataParser<E, T> parser) {
        this(requestText, null, parser);
    }

    protected CompositeDataInputRequest(String requestText, String commentText, CompositeDataParser<E, T> parser) {
        super(requestText, commentText);

        this.parser = parser;
        this.fieldRequests = new LinkedHashMap<>();
    }

    public CompositeDataInputRequest<E, T> addFieldRequest(
            E field, InputRequest<?> request) {
        fieldRequests.put(field, request);

        return this;
    }

    @FunctionalInterface
    public interface CompositeDataParser<E, T> {
        T parse(Map<E, Object> data) throws DataParserException;
    }
}