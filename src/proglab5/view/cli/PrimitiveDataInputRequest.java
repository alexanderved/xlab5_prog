package proglab5.view.cli;

import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.UnexpectedEODException;

final class PrimitiveDataInputRequest<T>
        extends proglab5.view.inputrequests.PrimitiveDataInputRequest<T> {
    private Cli cli;

    PrimitiveDataInputRequest(
            Cli cli, String requestText, PrimitiveDataParser<T> parser) {
        this(cli, requestText, null, parser);
    }

    PrimitiveDataInputRequest(
            Cli cli, String requestText, String commentText, PrimitiveDataParser<T> parser) {
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
            try {
                String fieldString;
                if (getCommentText() != null) {
                    fieldString = cli.input("Введите " + getRequestText() + " (" + getCommentText() + "): ");
                } else {
                    fieldString = cli.input("Введите " + getRequestText() + ": ");
                }
                fieldString = fieldString.isEmpty() ? null : fieldString;

                return parser.parse(fieldString);
            } catch (DataParserException e) {
                cli.error("Ошибка ввода: " + e.getMessage());
            }
        }
    }
}
