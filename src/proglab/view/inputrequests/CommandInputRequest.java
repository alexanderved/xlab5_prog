package proglab.view.inputrequests;

import proglab.controller.Runner;
import proglab.controller.commands.Command;
import proglab.controller.commands.CommandParser;

public abstract class CommandInputRequest extends InputRequest<Command> {
    protected final Runner runner;
    protected final CommandParser parser;

    protected CommandInputRequest(Runner runner, CommandParser parser) {
        this.runner = runner;
        this.parser = parser;
    }
}