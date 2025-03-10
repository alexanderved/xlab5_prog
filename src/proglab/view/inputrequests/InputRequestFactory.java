package proglab.view.inputrequests;

import proglab.controller.Runner;
import proglab.controller.commands.CommandParser;
import proglab.view.inputrequests.CompositeDataInputRequest.CompositeDataParser;
import proglab.view.inputrequests.PrimitiveDataInputRequest.PrimitiveDataParser;

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