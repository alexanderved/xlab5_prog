package proglab5.view.inputrequests;

public abstract class OptionalDataInputRequest<T> extends DataInputRequest<T> {
    protected DataInputRequest<T> req;

    protected OptionalDataInputRequest(DataInputRequest<T> req) {
        super(req.getRequestText(), req.getCommentText());

        this.req = req;
    }
}
