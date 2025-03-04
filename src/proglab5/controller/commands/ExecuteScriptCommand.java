package proglab5.controller.commands;

import java.io.IOException;
import proglab5.controller.Runner;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.exceptions.UnsupportedCommandException;
import proglab5.view.fileinput.FileInputView;

/**
 * Команда 'execute_script', считывающая и исполняющая скрипт из указанного файла.
 */
public class ExecuteScriptCommand extends Command {
    private static final String CMD_NAME = "execute_script";
    private static final String CMD_ARG_FORMAT = "file_name";
    private static final String CMD_DESCR = "считать и исполнить скрипт из указанного файла."
            + " В скрипте содержатся команды в таком же виде,"
            + " в котором их вводит пользователь в интерактивном режиме";
    private static final int NB_ARGUMENTS = 1;

    private final String script;

    private ExecuteScriptCommand(String script, Runner runner) {
        super(runner);

        this.script = script;
    }

    @Override
    public void execute() {
        runner.getView().output("Начинается исполнение скрипта `" + script + "`");

        try (FileInputView fileInputView = new FileInputView(script, runner.getView())) {
            Runner scripRunner = new Runner(
                fileInputView, runner.getCollectionManager(), runner.getCommandParser());
            scripRunner.run();

            runner.getView().output("Исполнение скрипта `" + script + "` завершено");
        } catch (IOException e) {
            runner.getView().error("Ошибка исполнения скрипта: " + e.getMessage());
        }
    }

    /**
     * Парсер команды 'execute_script'.
     */
    public static final class Parser extends CommandParser {
        private static final String CMD_INFO = CMD_NAME
                + " " + CMD_ARG_FORMAT + ": " + CMD_DESCR;

        @Override
        public String listCommandsInfo() {
            return CMD_INFO;
        }

        @Override
        public Command parse(String cmd, Runner runner)
        throws
            WrongNumberOfArgumentsException,
            UnsupportedCommandException
        {
            String[] args = cmd.split(" ");
            if (!args[0].equals(CMD_NAME)) {
                throw new UnsupportedCommandException(args[0]);
            }

            if (args.length - 1 != NB_ARGUMENTS) {
                throw new WrongNumberOfArgumentsException(args[0],
                        args.length - 1, NB_ARGUMENTS);
            }

            return new ExecuteScriptCommand(args[1], runner);
        }
    }
}
