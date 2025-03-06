package proglab5.view.cli;

import java.util.HashMap;
import java.util.Map;

import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;

class CompositeDataInputRequest<E, T>
        extends proglab5.view.inputrequests.CompositeDataInputRequest<E, T> {
    private Cli cli;

    CompositeDataInputRequest(
            Cli cli, String requestText, CompositeDataParser<E, T> parser) {
        this(cli, requestText, null, parser);
    }

    CompositeDataInputRequest(
            Cli cli, String requestText, String commentText, CompositeDataParser<E, T> parser) {
        super(requestText, commentText, parser);

        this.cli = cli;
    }

    @Override
    public Cli getView() {
        return cli;
    }

    @Override
    public T request() throws InputDeniedException, UnexpectedEODException {
        while (true) {
            if (getCommentText() != null) {
                cli.output("Введите " + getRequestText() + " (" + getCommentText() + "): ");
            } else {
                cli.output("Введите " + getRequestText() + ": ");
            }

            Map<E, Object> data = new HashMap<>();
            for (var entry : fieldRequests.entrySet()) {
                try {
                    data.put(entry.getKey(), entry.getValue().request());
                } catch (InputFailedException e) {
                    cli.error("Не удалось получить поле `"
                            + entry.getKey() + "`: " + e.getMessage());
                }
            }

            try {
                return parser.parse(data);
            } catch (DataParserException e) {
                cli.error("Ошибка ввода: " + e.getMessage());
            }
        }
    }
}
