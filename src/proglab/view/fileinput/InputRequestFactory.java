package proglab.view.fileinput;

import proglab.controller.Runner;
import proglab.controller.commands.CommandParser;
import proglab.view.inputrequests.CompositeDataInputRequest;
import proglab.view.inputrequests.DataInputRequest;
import proglab.view.inputrequests.PrimitiveDataInputRequest;
import proglab.view.inputrequests.CompositeDataInputRequest.CompositeDataParser;
import proglab.view.inputrequests.PrimitiveDataInputRequest.PrimitiveDataParser;

class InputRequestFactory extends proglab.view.inputrequests.InputRequestFactory {
    private FileInputView fileInputView;

    InputRequestFactory(FileInputView fileInputView) {
        this.fileInputView = fileInputView;
    }

    @Override
    public <T> PrimitiveDataInputRequest<T> createPrimitiveDataInputRequest(
            String requestText, String commentText, PrimitiveDataParser<T> parser) {
        return new proglab.view.fileinput.PrimitiveDataInputRequest<>(fileInputView, requestText, commentText, parser);
    }

    @Override
    public <E, T> CompositeDataInputRequest<E, T> createCompositeDataInputRequest(
            String requestText, String commentText, CompositeDataParser<E, T> parser) {
        return new proglab.view.fileinput.CompositeDataInputRequest<>(fileInputView, requestText, commentText, parser);
    }

    public <T> OptionalDataInputRequest<T> createOptionalInputRequest(DataInputRequest<T> req) {
        return new proglab.view.fileinput.OptionalDataInputRequest<>(fileInputView, req);
    }

    @Override
    public CommandInputRequest createCommandInputRequest(
            Runner runner, CommandParser parser) {
        return new proglab.view.fileinput.CommandInputRequest(fileInputView, runner, parser);
    }
}
