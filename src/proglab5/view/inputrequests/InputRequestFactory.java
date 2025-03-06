package proglab5.view.inputrequests;

import proglab5.controller.Runner;
import proglab5.controller.commands.CommandParser;
import proglab5.view.inputrequests.CompositeDataInputRequest.CompositeDataParser;
import proglab5.view.inputrequests.PrimitiveDataInputRequest.PrimitiveDataParser;

public abstract class InputRequestFactory {
    public abstract <T> PrimitiveDataInputRequest<T> createPrimitiveDataInputRequest(
            String requestText, String commentText, PrimitiveDataParser<T> parser);

    public abstract <E, T> CompositeDataInputRequest<E, T> createCompositeDataInputRequest(
            String requestText, String commentText, CompositeDataParser<E, T> parser);

    public abstract <T> OptionalDataInputRequest<T> createOptionalInputRequest(DataInputRequest<T> req);

    public abstract CommandInputRequest createCommandInputRequest(
            Runner runner, CommandParser parser);

    public <T> PrimitiveDataInputRequest<T> createPrimitiveDataInputRequest(
            String requestText, PrimitiveDataParser<T> parser) {
        return createPrimitiveDataInputRequest(requestText, null, parser);
    }

    public <E, T> CompositeDataInputRequest<E, T> createCompositeDataInputRequest(
            String requestText, CompositeDataParser<E, T> parser) {
        return createCompositeDataInputRequest(requestText, null, parser);
    }
}