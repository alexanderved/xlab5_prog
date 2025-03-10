package proglab.controller.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import proglab.controller.Runner;
import proglab.exceptions.InvalidArgumentFormatException;
import proglab.exceptions.UnexpectedEODException;
import proglab.exceptions.UnsupportedCommandException;
import proglab.exceptions.WrongNumberOfArgumentsException;

/**
 * Парсер, который может обрабатывать различные команды.
 */
public class GeneralCommandParser extends CommandParser {
    private Map<String, CommandParser> parsers = new LinkedHashMap<>();

    @Override
    public String listCommandsInfo() {
        ArrayList<String> infos = new ArrayList<>();
        for (CommandParser parser : parsers.values()) {
            String info = parser.listCommandsInfo();
            if (info != null) {
                infos.add(info);
            }
        }

        return String.join("\n", infos);
    }

    @Override
    public Command parse(String cmd, Runner runner)
    throws
        WrongNumberOfArgumentsException,
        InvalidArgumentFormatException,
        UnsupportedCommandException,
        UnexpectedEODException
    {
        String cmdName = cmd.split(" ")[0];
        if (!parsers.containsKey(cmdName)) {
            throw new UnsupportedCommandException(cmdName);
        }

        return parsers.get(cmdName).parse(cmd, runner);
    }

    /**
     * Добавляет парсер для одной из команд.
     * @param cmd Имя команды, которую будет обрабатывать парсер
     * @param parser Парсер команды
     */
    public void register(String cmd, CommandParser parser) {
        parsers.put(cmd, parser);
    }
}