package proglab5.controller.commands;

import proglab5.controller.Runner;
import proglab5.exceptions.InvalidArgumentFormatException;
import proglab5.exceptions.WrongNumberOfArgumentsException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.exceptions.UnsupportedCommandException;

/**
 * Парсер команд.
 */
public abstract class CommandParser implements CommandsInfoSource {
    /**
     * Парсит строку и возвращает команду, созданную на основе нее.
     * @param cmd Строка с командой
     * @param runner Исполнитель команды
     * @return команду полученную после парсинга строки
     * @throws WrongNumberOfArgumentsException если строка с командой содержит
     *                                         неверное число аргументов
     * @throws InvalidArgumentFormatException если формат аргументов команд неверен
     * @throws UnsupportedCommandException если парсер не может создать команду,
     *                                     соответствующую переданной строке
     * @throws UnexpectedEODException если парсер не может получить все необходимые
     *                                данные для создания команды
     * @throws IllegalArgumentException если исполнитель команды null
     */
    public abstract Command parse(String cmd, Runner runner)
    throws
        WrongNumberOfArgumentsException,
        InvalidArgumentFormatException,
        UnsupportedCommandException,
        UnexpectedEODException;
}