package proglab5.view.inputrequests;

import proglab5.controller.Runner;
import proglab5.controller.commands.Command;
import proglab5.controller.commands.CommandParser;

public abstract class CommandInputRequest extends InputRequest<Command> {
    protected final Runner runner;
    protected final CommandParser parser;

    protected CommandInputRequest(Runner runner, CommandParser parser) {
        this.runner = runner;
        this.parser = parser;
    }
}