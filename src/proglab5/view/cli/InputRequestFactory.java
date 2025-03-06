package proglab5.view.cli;

import proglab5.controller.Runner;
import proglab5.controller.commands.CommandParser;
import proglab5.view.inputrequests.CompositeDataInputRequest;
import proglab5.view.inputrequests.CompositeDataInputRequest.CompositeDataParser;
import proglab5.view.inputrequests.DataInputRequest;
import proglab5.view.inputrequests.PrimitiveDataInputRequest;
import proglab5.view.inputrequests.PrimitiveDataInputRequest.PrimitiveDataParser;

class InputRequestFactory extends proglab5.view.inputrequests.InputRequestFactory {
    private Cli cli;

    InputRequestFactory(Cli cli) {
        this.cli = cli;
    }

    @Override
    public <T> PrimitiveDataInputRequest<T> createPrimitiveDataInputRequest(
            String requestText, String commentText, PrimitiveDataParser<T> parser) {
        return new proglab5.view.cli.PrimitiveDataInputRequest<>(cli, requestText, commentText, parser);
    }

    @Override
    public <E, T> CompositeDataInputRequest<E, T> createCompositeDataInputRequest(
            String requestText, String commentText, CompositeDataParser<E, T> parser) {
        return new proglab5.view.cli.CompositeDataInputRequest<>(cli, requestText, commentText, parser);
    }

    public <T> OptionalDataInputRequest<T> createOptionalInputRequest(DataInputRequest<T> req) {
        return new proglab5.view.cli.OptionalDataInputRequest<>(cli, req);
    }

    @Override
    public CommandInputRequest createCommandInputRequest(
            Runner runner, CommandParser parser) {
        return new proglab5.view.cli.CommandInputRequest(cli, runner, parser);
    }
}
