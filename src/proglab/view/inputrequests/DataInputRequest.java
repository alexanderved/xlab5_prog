package proglab.view.inputrequests;

public abstract class DataInputRequest<T> extends InputRequest<T> {
    private final String requestText;
    private final String commentText;

    protected DataInputRequest(String requestText) {
        this(requestText, null);
    }

    protected DataInputRequest(String requestText, String commentText) {
        this.requestText = requestText;
        this.commentText = commentText;
    }

    public String getRequestText() {
        return requestText;
    }

    public String getCommentText() {
        return commentText;
    }
}