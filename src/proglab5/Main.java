package proglab5;

import proglab5.application.IDGenerator;
import proglab5.application.IncrementalIDGenerator;
import proglab5.application.CollectionManager;
import proglab5.controller.Runner;
import proglab5.controller.commands.AddCommand;
import proglab5.controller.commands.AddIfMaxCommand;
import proglab5.controller.commands.AddIfMinCommand;
import proglab5.controller.commands.AverageOfEmployeesCountCommand;
import proglab5.controller.commands.ClearCommand;
import proglab5.controller.commands.EmptyCommand;
import proglab5.controller.commands.ExecuteScriptCommand;
import proglab5.controller.commands.ExitCommand;
import proglab5.controller.commands.FilterStartsWithFullNameCommand;
import proglab5.controller.commands.GeneralCommandParser;
import proglab5.controller.commands.HelpCommand;
import proglab5.controller.commands.InfoCommand;
import proglab5.controller.commands.PrintFieldDescendingAnnualTurnoverCommand;
import proglab5.controller.commands.RemoveByIDCommand;
import proglab5.controller.commands.RemoveLowerCommand;
import proglab5.controller.commands.SaveCommand;
import proglab5.controller.commands.ShowCommand;
import proglab5.controller.commands.UpdateCommand;
import proglab5.exceptions.RepositoryAccessDeniedException;
import proglab5.exceptions.RepositoryDataCorruptedException;
import proglab5.repository.xml.XmlRepository;
import proglab5.view.cli.Cli;
import proglab5.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Cli();

        String repoPath = System.getenv("REPO_PATH");
        if (repoPath == null) {
            view.error("Файл репозитория не найден."
                + " Необходимо установить переменную окружения `REPO_PATH`.");

            return;
        }

        IDGenerator idGen = new IncrementalIDGenerator();
        XmlRepository repo = new XmlRepository(repoPath);
        CollectionManager collection;
        try {
            collection = new CollectionManager(idGen, repo);
        } catch (RepositoryAccessDeniedException | RepositoryDataCorruptedException e) {
            view.error(e.getMessage());

            return;
        }

        GeneralCommandParser cmdParser = new GeneralCommandParser();
        Runner runner = new Runner(view, collection, cmdParser);

        cmdParser.register("", new EmptyCommand.Parser());
        cmdParser.register("help", new HelpCommand.Parser());
        cmdParser.register("info", new InfoCommand.Parser());
        cmdParser.register("show", new ShowCommand.Parser());
        cmdParser.register("add", new AddCommand.Parser());
        cmdParser.register("update", new UpdateCommand.Parser());
        cmdParser.register("remove_by_id", new RemoveByIDCommand.Parser());
        cmdParser.register("clear", new ClearCommand.Parser());
        cmdParser.register("save", new SaveCommand.Parser());
        cmdParser.register("execute_script", new ExecuteScriptCommand.Parser());
        cmdParser.register("exit", new ExitCommand.Parser());
        cmdParser.register("add_if_max", new AddIfMaxCommand.Parser());
        cmdParser.register("add_if_min", new AddIfMinCommand.Parser());
        cmdParser.register("remove_lower", new RemoveLowerCommand.Parser());
        cmdParser.register("average_of_employees_count",
            new AverageOfEmployeesCountCommand.Parser());
        cmdParser.register("filter_starts_with_full_name",
            new FilterStartsWithFullNameCommand.Parser());
        cmdParser.register("print_field_descending_annual_turnover",
            new PrintFieldDescendingAnnualTurnoverCommand.Parser());

        runner.run();
    }
}