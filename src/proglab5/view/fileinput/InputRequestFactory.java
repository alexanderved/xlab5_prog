package proglab5.view.fileinput;

import proglab5.controller.Runner;
import proglab5.controller.commands.CommandParser;
import proglab5.view.inputrequests.CompositeDataInputRequest;
import proglab5.view.inputrequests.CompositeDataInputRequest.CompositeDataParser;
import proglab5.view.inputrequests.DataInputRequest;
import proglab5.view.inputrequests.PrimitiveDataInputRequest;
import proglab5.view.inputrequests.PrimitiveDataInputRequest.PrimitiveDataParser;

class InputRequestFactory extends proglab5.view.inputrequests.InputRequestFactory {
    private FileInputView fileInputView;

    InputRequestFactory(FileInputView fileInputView) {
        this.fileInputView = fileInputView;
    }

    @Override
    public <T> PrimitiveDataInputRequest<T> createPrimitiveDataInputRequest(
            String requestText, String commentText, PrimitiveDataParser<T> parser) {
        return new proglab5.view.fileinput.PrimitiveDataInputRequest<>(fileInputView, requestText, commentText, parser);
    }

    @Override
    public <E, T> CompositeDataInputRequest<E, T> createCompositeDataInputRequest(
            String requestText, String commentText, CompositeDataParser<E, T> parser) {
        return new proglab5.view.fileinput.CompositeDataInputRequest<>(fileInputView, requestText, commentText, parser);
    }

    public <T> OptionalDataInputRequest<T> createOptionalInputRequest(DataInputRequest<T> req) {
        return new proglab5.view.fileinput.OptionalDataInputRequest<>(fileInputView, req);
    }

    @Override
    public CommandInputRequest createCommandInputRequest(
            Runner runner, CommandParser parser) {
        return new proglab5.view.fileinput.CommandInputRequest(fileInputView, runner, parser);
    }
}
